package com.mcp.osc.infrastructure.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author KG
 * @description 分页数据
 * @date Created in 2022年09月21日 9:59 AM
 * @modified_by
 */
@Data
@NoArgsConstructor
public class PageData<T> {
    /**
        总条数
    */
    private long totalCount;

    /**
        当前页
     */
    private long currentPage;

    /**
        当页数据
     */
    private List<T> data;
}
