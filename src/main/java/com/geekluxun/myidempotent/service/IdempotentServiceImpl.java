package com.geekluxun.myidempotent.service;

import com.geekluxun.myidempotent.constant.BizOptResponseEnum;
import com.geekluxun.myidempotent.dto.common.RequestDto;
import com.geekluxun.myidempotent.dto.common.ResponseDto;
import com.geekluxun.myidempotent.dto.idempotent.BizOptReqResultDto;
import com.geekluxun.myidempotent.dto.idempotent.BizOptRequestDto;
import com.geekluxun.myidempotent.dto.idempotent.BizOptResultDto;
import com.geekluxun.util.DigestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * Copyright,2018-2019,geekluxun Co.,Ltd.
 *
 * @Author: luxun
 * @Create: 2019-01-14 15:38
 * @Description:
 * @Other:
 */
@Service
@Slf4j
@com.alibaba.dubbo.config.annotation.Service
public class IdempotentServiceImpl implements IdempotentService {

    /**
     * 默认使用StringRedisSerializer 序列化key 和 value
     * RedisTemplate使用的是JdkSerializationRedisSerializer方式
     */
    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public ResponseDto<BizOptReqResultDto> requestBusinessOperate(RequestDto<BizOptRequestDto> requestDto) {
        ResponseDto<BizOptReqResultDto> responseDto = new ResponseDto<>();
        String businessId = requestDto.getRequestPara().getBusinessId();
        String globalId = DigestUtils.md5Str(businessId.getBytes());
        System.out.println("globalId:" + globalId);
        Long expire = requestDto.getRequestPara().getIdempotentExpireTime();
        String value = System.currentTimeMillis() + businessId;

        BizOptReqResultDto resultDto = new BizOptReqResultDto();
        resultDto.setBusinessId(businessId);
        resultDto.setResultValue(value);
        resultDto.setIdempotentExpireTime(expire);

        Boolean result;
        try {
            result = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    Boolean result = connection.stringCommands().setNX(globalId.getBytes(), value.getBytes());
                    if (result) {
                        connection.keyCommands().expire(globalId.getBytes(), expire);
                    }
                    return result;
                }
            });
            resultDto.setCanBizOpt(result);
        } catch (DataAccessException e) {
            // 超时或者访问redis异常
            log.error("访问redis异常", e);
            redisTemplate.delete(globalId);
            resultDto.setCanBizOpt(false);
        }

        responseDto.setData(resultDto);
        return responseDto;
    }

    @Override
    public ResponseDto<BizOptResponseEnum> handleBusinessOperateResult(RequestDto<BizOptResultDto> reqeustDto) {
        ResponseDto<BizOptResponseEnum> responseDto = new ResponseDto<>();

        BizOptResultDto resultPara = reqeustDto.getRequestPara();
        String key = DigestUtils.md5Str(resultPara.getBusinessId().getBytes());
        System.out.println("key:" + key);
        // 业务操作成功 
        if (resultPara.getBizResult()) {
            String value = (String) redisTemplate.execute(new RedisCallback() {
                @Override
                public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    byte[] value = redisConnection.stringCommands().get(key.getBytes());
                    String result = new String(value, Charset.defaultCharset());
                    return result;
                }
            });

            // 是自己的操作
            if (value.equals(resultPara.getResultValue())) {
                Boolean result = redisTemplate.expire(key, resultPara.getIdempotentExpireTime(), TimeUnit.SECONDS);
                if (!result) {
                    throw new RuntimeException("设置过期时间错误");
                }
                responseDto.setData(BizOptResponseEnum.SUCCESS);
            } else {
                // 业务操作超时
                responseDto.setData(BizOptResponseEnum.TIMEOUT);
                return responseDto;
            }

        } else {
            String value = redisTemplate.opsForValue().get(key);
            // 业务操作失败处理
            if (value == resultPara.getResultValue()) {
                Boolean result = redisTemplate.delete(key);
                if (!result) {
                    throw new RuntimeException("删除redis错误");
                }
                responseDto.setData(BizOptResponseEnum.FAILURE);
            } else {
                responseDto.setData(BizOptResponseEnum.SUCCESS);
                return responseDto;
            }
        }
        return responseDto;
    }
}
