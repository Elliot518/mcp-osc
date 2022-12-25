package com.mcp.osc.infrastructure.api;

import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

/**
 * @author: KG
 * @description: Unified Json Format Annotation
 * @date: Created in 10:33 上午 2022/9/21
 * @modified by:
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ResponseBody
public @interface ResponseResult {
}
