package com.geekluxun.myidempotent.dto.idempotent;

import lombok.Data;

/**
 * Copyright,2018-2019,geekluxun Co.,Ltd.
 *
 * @Author: luxun
 * @Create: 2019-01-15 16:09
 * @Description:
 * @Other:
 */
@Data
public class BizOptResultDto {

    /**
     * 业务操作结果（成功或失败 ）
     */
    private Boolean bizResult;

    /**
     * 业务操作唯一标识(key)
     */
    private String businessId;

    /**
     * 请求结果值(value)
     */
    private String resultValue;

    /**
     * 幂等操作过期时间（秒 expire ）
     */
    private Long idempotentExpireTime;
}
