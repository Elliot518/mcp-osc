package com.mcp.osc.infrastructure.exception;

/**
 * @author KG(Kelvin Gu)
 * @ClassName: ExcelException
 * @Package com.jnj.autocloud.common.exception
 * @Description: excel exception
 * @date 2022/9/21 20:10
 */
public class ExcelException extends RuntimeException {
    public ExcelException(String msg) {
        super(msg);
    }
}
