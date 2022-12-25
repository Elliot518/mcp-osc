package com.mcp.osc.domain.exception;

import com.mcp.osc.infrastructure.exception.ApiException;

/**
 * @author KG
 * @description 业务自定义异常
 * @date Created in 2022年09月21日 9:55 AM
 * @modified_by
 */
public class BusinessException extends ApiException {
    public BusinessException(BusinessResultCode resultCode) {
        super(resultCode.getCode(), resultCode.getMessage());
    }

    public BusinessException(String code, String message) {
        super(code, message);
    }
}


