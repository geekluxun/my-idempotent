package com.geekluxun.myidempotent.dto.idempotent;

import com.geekluxun.myidempotent.dto.common.ResponseDataDto;
import lombok.Data;

/**
 * Copyright,2018-2019,geekluxun Co.,Ltd.
 *
 * @Author: luxun
 * @Create: 2019-01-15 16:04
 * @Description:
 * @Other:
 */
@Data
public class BizOptReqResultDto extends ResponseDataDto {
    /**
     * 是否允许业务操作
     */
    private Boolean canBizOpt;

    /**
     * 业务操作唯一标识
     */
    private String businessId;

    /**
     * 请求结果值
     */
    private String resultValue;

    /**
     * 幂等操作过期时间（秒 ）
     */
    private Long idempotentExpireTime;
}
