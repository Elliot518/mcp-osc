package com.mcp.osc.domain.models.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author KG
 * @description
 * @date Created in 2022年09月21日 2:38 PM
 * @modified_by
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchQueueVo {
    /**
     * 批次号
     */
    private String batchId;

    /**
     * 文件数量
     */
    private BigDecimal filesNum;

    /**
     * 状态
     */
    private String status;

    /**
     * 任务完成百分比
     */
    private BigDecimal completePercentage;

    /**
     * 开始时间
     */
    private Date createTime;

    /**
     * 结束时间
     */
    private Date lastUpdate;
}


