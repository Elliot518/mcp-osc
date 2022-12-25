package com.mcp.osc.domain.log;

import com.mcp.osc.infrastructure.log.ILogFileName;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: KG
 * @description:
 * @date: Created in 19:47 2022/9/21
 * @modified by:
 */
public enum LogFileName implements ILogFileName {
    USER_LAKE("userLake");

    private String logFileName;

    LogFileName(String fileName) {
        this.logFileName = fileName;
    }

    @Override
    public String getLogFileName() {
        return logFileName;
    }

    @Override
    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public static LogFileName getByName(String value) {
        LogFileName[] arr = values();
        for (LogFileName item : arr) {
            if (null != item && StringUtils.isNotBlank(item.logFileName) && value.equals(item.getLogFileName())) {
                return item;
            }
        }
        return null;
    }
}
