package com.geekluxun.myidempotent.dto.idempotent;

import com.geekluxun.myidempotent.dto.common.RequestParaDto;
import lombok.Data;

/**
 * Copyright,2018-2019,geekluxun Co.,Ltd.
 *
 * @Author: luxun
 * @Create: 2019-01-14 15:52
 * @Description: 业务操作请求DTO
 * @Other:
 */
@Data
public class BizOptRequestDto extends RequestParaDto {
    /**
     * 业务操作唯一标识
     */
    private String businessId;

    /**
     * 幂等操作过期时间（秒 ）
     */
    private Long idempotentExpireTime;
}
