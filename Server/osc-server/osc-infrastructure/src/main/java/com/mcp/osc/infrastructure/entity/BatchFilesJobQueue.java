package com.mcp.osc.infrastructure.entity;

import java.math.BigDecimal;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 批量文件工作队列表
 * </p>
 *
 * @author KG
 * @since 2021-09-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BatchFilesJobQueue implements Serializable {

    private static final long serialVersionUID = -585406434542623042L;

    /**
     * 主键ID(批次号)
     */
    private String sid;

    /**
     * 任务完成百分比
     */
    private BigDecimal completePercentage;

    /**
     * 批量任务状态(0:待解析 1:解析中 2:待分词 3:分词中 4:分词完毕)
     */
    private BigDecimal batchJobStatus;

    /**
     * 文件数量
     */
    private BigDecimal filesNum;

    /**
     * 已解析文件数量
     */
    private BigDecimal analyzedFilesNum;

    /**
     * 已处理文件数量
     */
    private BigDecimal segmentedFilesNum;

    /**
     * 记录插入时间
     */
    private LocalDateTime createTime;

    /**
     * 记录最后更新时间
     */
    private LocalDateTime lastUpdate;
}











