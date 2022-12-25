package com.mcp.osc.domain.repository.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcp.osc.domain.repository.FileJobQueueRepository;
import com.mcp.osc.infrastructure.entity.FileJobQueue;
import com.mcp.osc.infrastructure.mapper.FileJobQueueMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 单个文件工作队列表 服务实现类
 * </p>
 *
 * @author KG
 * @since 2021-09-14
 */
@Repository
public class FileJobQueueRepositoryImpl extends ServiceImpl<FileJobQueueMapper, FileJobQueue> implements FileJobQueueRepository {
    @Override
    public List<FileJobQueue> listByBatchId(String batchId) {
        return this.baseMapper.selectList(new QueryWrapper<FileJobQueue>()
                .eq("batch_id", batchId)
        );
    }

    @Override
    public List<FileJobQueue> listByBatchIdAndStatus(String batchId, BigDecimal fileJobStatus) {
        return this.baseMapper.selectList(new QueryWrapper<FileJobQueue>()
                .eq("batch_id", batchId)
                .eq("file_job_status", fileJobStatus)
        );
    }

    @Override
    public int updateBySid(String sid, FileJobQueue fileJobQueue) {
        UpdateWrapper<FileJobQueue> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("sid", sid);

        return this.baseMapper.update(fileJobQueue, updateWrapper);
    }
}




