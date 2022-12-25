package com.mcp.osc.domain.service.components;



import com.mcp.osc.domain.models.constant.BatchJobStatus;
import com.mcp.osc.domain.models.dto.BatchQueueDto;
import com.mcp.osc.domain.models.dto.BatchUploadDto;
import com.mcp.osc.domain.models.vo.BatchQueueVo;
import com.mcp.osc.infrastructure.data.PageData;
import com.mcp.osc.infrastructure.entity.BatchFilesJobQueue;

import java.util.List;

/**
 * @author KG
 * @description 批量队列任务服务接口
 * @date Created in 2021年09月15日 1:39 PM
 * @modified_by
 */
public interface IBatchQueueService {
    /**
     * 增加一条新的批量任务到队列
     * @author KG
     * @date 2022/9/21 1:56 PM
     * @param dto 批量队列请求
     * @return java.lang.String 批次号
     */
    String addNew(BatchUploadDto dto);

    /**
     * 通过批次号获取待解析文件队列
     * @author KG
     * @date 2022/9/19 11:01 AM
     * @param batchId
     * @return com.mcp.geek.domain.models.dto.BatchQueueDto
     */
    BatchQueueDto getReadyToAnalyzeQueuesByBatchId(String batchId);

    /**
     * 查询待解析批量队列列表
     * @author KG
     * @date 2022/9/19 10:56 AM
     * @return java.util.List<com.mcp.geek.infrastructure.entity.BatchFilesJobQueue>
     */
    List<BatchFilesJobQueue> listReadyToAnalyzeBatchQueues();

    /**
     * 通过批次号更新批量任务队列状态
     * @author KG
     * @date 2021/9/18 2:24 PM
     * @param batchId 批次号
     * @param jobStatus 状态
     */
    void updateBatchQueueStatusById(String batchId, BatchJobStatus jobStatus);

    /**
     * 分页显示所有批量队列任务
     * @author KG
     * @date 2022/9/19 10:50 AM
     * @param pageNumber 第几页
     * @param pageSize 每页数量
     * @return com.mcp.geek.infrastructure.data.PageData<com.mcp.geek.domain.models.vo.BatchQueueVo>
     */
    PageData<BatchQueueVo> listAllBatchQueuesByPage(int pageNumber, int pageSize);
}

