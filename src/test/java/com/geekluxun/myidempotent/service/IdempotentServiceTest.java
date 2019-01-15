package com.geekluxun.myidempotent.service;

import com.geekluxun.myidempotent.constant.BizOptResponseEnum;
import com.geekluxun.myidempotent.dto.common.RequestDto;
import com.geekluxun.myidempotent.dto.common.ResponseDto;
import com.geekluxun.myidempotent.dto.idempotent.BizOptReqResultDto;
import com.geekluxun.myidempotent.dto.idempotent.BizOptRequestDto;
import com.geekluxun.myidempotent.dto.idempotent.BizOptResultDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * Copyright,2018-2019,geekluxun Co.,Ltd.
 *
 * @Author: luxun
 * @Create: 2019-01-15 14:09
 * @Description:
 * @Other:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IdempotentServiceTest {

    @Autowired
    IdempotentService idempotentService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * 正常用例
     */
    @Test
    public void nomalUserCase() throws Exception {
        BizOptRequestDto requestPara = new BizOptRequestDto();
        requestPara.setBusinessId("112233");
        requestPara.setIdempotentExpireTime(130L);
        ResponseDto<BizOptReqResultDto> responseDto = idempotentService.requestBusinessOperate(new RequestDto<BizOptRequestDto>(requestPara, 1));
        Assert.assertTrue(responseDto.getData().getCanBizOpt());

        System.out.println("业务操作开始...");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("业务操作结束...");

        BizOptResultDto requestPara2 = new BizOptResultDto();
        // 业务操作成功
        requestPara2.setBizResult(true);
        requestPara2.setBusinessId(responseDto.getData().getBusinessId());
        requestPara2.setIdempotentExpireTime(responseDto.getData().getIdempotentExpireTime());
        requestPara2.setResultValue(responseDto.getData().getResultValue());

        ResponseDto<BizOptResponseEnum> responseDto2 = idempotentService.handleBusinessOperateResult(new RequestDto<>(requestPara2, 1));

        Assert.assertTrue(responseDto2.getData().equals(BizOptResponseEnum.SUCCESS));

    }


}