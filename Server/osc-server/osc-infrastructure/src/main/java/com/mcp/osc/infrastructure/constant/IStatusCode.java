package com.mcp.osc.infrastructure.constant;

/**
 * @author: KG
 * @description:
 * @date: Created in 19:25 2022/9/21
 * @modified by:
 */
public interface IStatusCode {
    /**
     * 获取状态ID
     * @author KG
     * @date 2021/9/19 10:31 AM
     * @return int
     */
    int getId();

    /**
     * 获取状态描述
     * @author KG
     * @date 2021/9/19 10:31 AM
     * @return java.lang.String
     */
    String getStatus();
}