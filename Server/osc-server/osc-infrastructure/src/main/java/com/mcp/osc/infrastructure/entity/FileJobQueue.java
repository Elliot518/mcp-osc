package com.mcp.osc.infrastructure.entity;

import java.math.BigDecimal;

import java.io.Serializable;
import java.util.Date;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 单个文件工作队列表
 * </p>
 *
 * @author KG
 * @since 2022-09-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileJobQueue implements Serializable {

    private static final long serialVersionUID = -6831962427393680379L;

    /**
     * 主键ID
     */
    private String sid;

    /**
     * 批次号
     */
    private String batchId;

    /**
     * 原始上传文件名
     */
    private String originalFileName;

    /**
     * 上传后的文件名
     */
    private String uploadedFileName;

    /**
     * 文件任务状态(0:待解析 1:解析中 2:待处理 3:处理中 4:处理完毕 9:解析失败)
     */
    private BigDecimal fileJobStatus;

    /**
     * 记录插入时间
     */
    private Date createTime;

    /**
     * 记录最后更新时间
     */
    private Date lastUpdate;
}






