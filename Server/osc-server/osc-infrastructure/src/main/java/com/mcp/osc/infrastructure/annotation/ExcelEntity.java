package com.mcp.osc.infrastructure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author KG(Kelvin Gu)
 * @ClassName: ExcelEntity
 * @Package org.genesis.javacollector.annotation.excel.annotation
 * @Description: excel entity annotation
 * @date 2022/9/21 09:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelEntity {
}

