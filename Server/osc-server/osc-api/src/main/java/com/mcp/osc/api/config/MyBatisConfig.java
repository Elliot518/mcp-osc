package com.mcp.osc.api.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: KG
 * @description: 配置类(拦截器/扫描mapper目录/自动管理事务)
 * @date: Created in 2022年09月21日 10:06 AM
 * @modified by:
 */
@MapperScan("com.mcp.osc.infrastructure.mapper")
@EnableTransactionManagement
@Configuration
public class MyBatisConfig {
    /**
     * 分页插件
     * @author KG
     * @date 2021/6/23 10:07 PM
     * @return com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);

        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);

        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));

        return paginationInterceptor;
    }
}



