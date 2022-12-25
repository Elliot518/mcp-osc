package com.mcp.osc.infrastructure.exception;

/**
 * @author: KG
 * @description:
 * @date: Created in 2:34 PM 2019/9/4
 * @modified by: KG 2022/9/21
 */

public class LogFormat {
    public static String apiErrorDetail(ApiException e) {
        return String.format("**** 自定义接口异常 -- Code: %s, Message: %s, Exception Cause: %s", e.getCode(), e.getMsg(), e.getMessage());
    }
}



