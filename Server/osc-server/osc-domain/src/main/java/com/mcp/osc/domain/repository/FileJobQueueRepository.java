package com.mcp.osc.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mcp.osc.infrastructure.entity.FileJobQueue;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 单个文件工作队列表 服务类
 * </p>
 *
 * @author KG
 * @since 2022-09-21
 */
public interface FileJobQueueRepository extends IService<FileJobQueue> {
    /**
     * 通过批次号查询文件队列列表
     * @author KG
     * @date 2022/9/19 11:33 AM
     * @param batchId 批次号
     * @return java.util.List<FileJobQueue>
     */
    List<FileJobQueue> listByBatchId(String batchId);

    /**
     * 通过批次号和状态查询文件队列列表
     * @author KG
     * @date 2022/9/21 2:01 PM
     * @param batchId 批次号
     * @param fileJobStatus 任务状态
     * @return java.util.List<FileJobQueue>
     */
    List<FileJobQueue> listByBatchIdAndStatus(String batchId, BigDecimal fileJobStatus);

    /**
     * 根据SID更新
     * @author KG
     * @date 2022/9/21 1:23 PM
     * @param sid pk
     * @param fileJobQueue 待更新队列
     * @return int
     */
    int updateBySid(String sid, FileJobQueue fileJobQueue);
}













