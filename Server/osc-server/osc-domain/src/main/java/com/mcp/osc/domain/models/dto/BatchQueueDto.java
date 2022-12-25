package com.mcp.osc.domain.models.dto;


import com.mcp.osc.infrastructure.entity.FileJobQueue;
import lombok.Data;

import java.util.List;

/**
 * @author KG
 * @description 单个批量队列传输对象(Master-Detail关系)
 * @date Created in 2022年09月21日 10:02 AM
 * @modified_by
 */
@Data
public class BatchQueueDto {
    /**
        主批次号
    */
    private String batchId;


    /**
        文件队列(从队列)
     */
    private List<FileJobQueue> fileQueues;
}
