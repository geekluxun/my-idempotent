package com.geekluxun.myidempotent.service;

import com.geekluxun.myidempotent.constant.BizOptResponseEnum;
import com.geekluxun.myidempotent.dto.common.RequestDto;
import com.geekluxun.myidempotent.dto.common.ResponseDto;
import com.geekluxun.myidempotent.dto.idempotent.BizOptReqResultDto;
import com.geekluxun.myidempotent.dto.idempotent.BizOptRequestDto;
import com.geekluxun.myidempotent.dto.idempotent.BizOptResultDto;

/**
 * Copyright,2018-2019,geekluxun Co.,Ltd.
 *
 * @Author: luxun
 * @Create: 2019-01-14 15:38
 * @Description:
 * @Other:
 */

public interface IdempotentService {
    /**
     * 请求业务操作
     *
     * @param requestDto
     * @return
     */
    ResponseDto<BizOptReqResultDto> requestBusinessOperate(RequestDto<BizOptRequestDto> requestDto);

    /**
     * 处理业务操作结果
     *
     * @param reqeustDto
     * @return
     */
    ResponseDto<BizOptResponseEnum> handleBusinessOperateResult(RequestDto<BizOptResultDto> reqeustDto);
}
