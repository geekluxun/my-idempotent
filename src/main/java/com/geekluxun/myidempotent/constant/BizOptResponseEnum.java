package com.geekluxun.myidempotent.constant;

/**
 * Copyright,2018-2019,geekluxun Co.,Ltd.
 *
 * @Author: luxun
 * @Create: 2019-01-15 16:36
 * @Description:
 * @Other:
 */
public enum BizOptResponseEnum {
    SUCCESS(1, "成功"),
    FAILURE(2, "失败"),
    TIMEOUT(3, "超时");

    private int result;
    private String msg;

    BizOptResponseEnum(int result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }


    public String getMsg() {
        return msg;
    }
}
