package com.mcp.osc.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcp.osc.domain.repository.BatchFilesJobQueueRepository;
import com.mcp.osc.infrastructure.data.PageData;
import com.mcp.osc.infrastructure.entity.BatchFilesJobQueue;
import com.mcp.osc.infrastructure.mapper.BatchFilesJobQueueMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 批量文件工作队列表 服务实现类
 * </p>
 * @author KG
 * @since 2022-09-22
 */
@Repository
public class BatchFilesJobQueueRepositoryImpl extends ServiceImpl<BatchFilesJobQueueMapper, BatchFilesJobQueue> implements BatchFilesJobQueueRepository {
    @Override
    public int addNew(BatchFilesJobQueue batchFilesJobQueue) {
        return this.getBaseMapper().insert(batchFilesJobQueue);
    }

    @Override
    public BatchFilesJobQueue getBySid(String sid) {
        return this.baseMapper.selectOne(new QueryWrapper<BatchFilesJobQueue>()
                .eq("sid", sid)
        );
    }

    @Override
    public List<BatchFilesJobQueue> listByStatus(BigDecimal jobStatus) {
        QueryWrapper<BatchFilesJobQueue> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wrapper -> wrapper.eq("batch_job_status", jobStatus));

        // 根据创建时间正序排列
        queryWrapper.orderByAsc("create_time");

        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public int updateBySid(String sid, BatchFilesJobQueue batchQueue) {
        UpdateWrapper<BatchFilesJobQueue> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("sid", sid);

        return this.baseMapper.update(batchQueue, updateWrapper);
    }

    @Override
    public PageData<BatchFilesJobQueue> listByPage(int pageNumber, int pageSize) {
        Page<BatchFilesJobQueue> batchQueuesPage = new Page<>(pageNumber, pageSize);

        QueryWrapper<BatchFilesJobQueue> queryWrapper = new QueryWrapper<>();
        // 根据开始时间倒序排列
        queryWrapper.orderByDesc("create_time");
        this.getBaseMapper().selectPage(batchQueuesPage, queryWrapper);

        List<BatchFilesJobQueue> dataList = batchQueuesPage.getRecords();

        PageData<BatchFilesJobQueue> pageData = new PageData<>();
        pageData.setTotalCount(batchQueuesPage.getTotal());
        pageData.setCurrentPage(pageNumber);
        pageData.setData(dataList);

        return pageData;
    }
}





