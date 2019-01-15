package com.geekluxun.myidempotent.dto.common;

import lombok.Data;

/**
 * Copyright,2018-2019,geekluxun Co.,Ltd.
 *
 * @Author: luxun
 * @Create: 2019-01-04 18:26
 * @Description:
 * @Other:
 */
@Data
public class RequestDto<T> extends BaseDto {
    T requestPara;
    long requestId;

    public RequestDto(T requestPara, long requestId) {
        this.requestPara = requestPara;
        this.requestId = requestId;
    }
}
