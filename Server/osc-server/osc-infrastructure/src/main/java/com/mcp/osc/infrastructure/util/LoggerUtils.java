package com.mcp.osc.infrastructure.util;

import com.mcp.osc.infrastructure.log.ILogFileName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: KG
 * @description:
 * @date: Created in 19:18 2022/9/21
 * @modified by:
 */
public class LoggerUtils {
    public static <T> Logger Logger(Class<T> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static Logger Logger(ILogFileName desc) {
        return LoggerFactory.getLogger(desc.getLogFileName());
    }
}
