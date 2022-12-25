package com.mcp.osc.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mcp.osc.infrastructure.data.PageData;
import com.mcp.osc.infrastructure.entity.BatchFilesJobQueue;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 批量文件工作队列表 服务类
 * </p>
 *
 * @author KG
 * @since 2022-09-21
 */
public interface BatchFilesJobQueueRepository extends IService<BatchFilesJobQueue> {
    /**
     * 新增一条批量队列
     * @author KG
     * @date 2022/09/22 8:24 PM
     * @param batchFilesJobQueue 批量队列
     * @return int
     */
    int addNew(BatchFilesJobQueue batchFilesJobQueue);

    /**
     * 通过主键批次号获取单条记录
     * @author KG
     * @date 2022/9/22 10:09 PM
     * @param sid 批次号
     * @return BatchFilesJobQueue
     */
    BatchFilesJobQueue getBySid(String sid);

    /**
     * 通过状态ID获取批量队列列表
     * @author KG
     * @date 2022/9/22 2:46 PM
     * @param jobStatus 状态ID
     * @return java.util.List<BatchFilesJobQueue>
     */
    List<BatchFilesJobQueue> listByStatus(BigDecimal jobStatus);

    /**
     * 根据SID更新
     * @author KG
     * @date 2022/9/22 2:12 PM
     * @param sid pk
     * @param batchQueue 待更新队列
     * @return int
     */
    int updateBySid(String sid, BatchFilesJobQueue batchQueue);

    /**
     * 分页显示批量任务队列
     * @author KG
     * @date 2022/9/22 11:19 AM
     * @param pageNumber 第几页
     * @param pageSize 每页数量
     * @return PageData<BatchFilesJobQueue>
     */
    PageData<BatchFilesJobQueue> listByPage(int pageNumber, int pageSize);
}






