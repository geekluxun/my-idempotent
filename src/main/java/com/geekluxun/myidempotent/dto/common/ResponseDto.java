package com.geekluxun.myidempotent.dto.common;

import lombok.Data;

import java.io.Serializable;

/**
 * Copyright,2018-2019,geekluxun Co.,Ltd.
 *
 * @Author: luxun
 * @Create: 2019-01-04 18:06
 * @Description:
 * @Other:
 */
@Data
public class ResponseDto<T> implements Serializable {
    private int retCode;
    private String retMsg;
    private T data;
    private long responseId;
}
