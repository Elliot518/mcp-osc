package com.mcp.osc.domain.service.components.impl;

import cn.hutool.core.date.DateUtil;

import com.mcp.osc.domain.convert.BatchQueueConverter;
import com.mcp.osc.domain.models.constant.BatchJobStatus;
import com.mcp.osc.domain.models.constant.FileJobStatus;
import com.mcp.osc.domain.models.constant.IBizPrefix;
import com.mcp.osc.domain.models.dto.BatchQueueDto;
import com.mcp.osc.domain.models.dto.BatchUploadDto;
import com.mcp.osc.domain.models.dto.FileDto;
import com.mcp.osc.domain.models.vo.BatchQueueVo;
import com.mcp.osc.domain.repository.BatchFilesJobQueueRepository;
import com.mcp.osc.domain.repository.FileJobQueueRepository;
import com.mcp.osc.domain.service.components.IBatchQueueService;
import com.mcp.osc.infrastructure.data.PageData;
import com.mcp.osc.infrastructure.entity.BatchFilesJobQueue;
import com.mcp.osc.infrastructure.entity.FileJobQueue;
import com.mcp.osc.infrastructure.generator.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KG
 * @description 批量队列任务服务实现
 * @date Created in 2022年09月22日 2:26 PM
 * @modified_by
 */
@Service
public class BatchQueueServiceImpl implements IBatchQueueService {

    @Autowired
    private BatchFilesJobQueueRepository batchFilesJobQueueRepository;

    @Autowired
    private FileJobQueueRepository fileJobQueueRepository;
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String addNew(BatchUploadDto dto) {
        /*
         * Step 1: Add one new batch queue record
         */
        String batchId = IdGenerator.getBizTimeId(IBizPrefix.BATCH_QUEUE);
        int filesNum = dto.getFilesNum();
        List<FileDto> fileDtoList = dto.getFileDtos();

        BatchFilesJobQueue batchFilesJobQueue = new BatchFilesJobQueue();
        batchFilesJobQueue.setSid(batchId);
        batchFilesJobQueue.setCompletePercentage(BigDecimal.ZERO);
        batchFilesJobQueue.setBatchJobStatus(new BigDecimal(BatchJobStatus.READY_TO_ANALYZE.getId()));
        batchFilesJobQueue.setFilesNum(new BigDecimal(filesNum));
        batchFilesJobQueue.setAnalyzedFilesNum(BigDecimal.ZERO);
        batchFilesJobQueue.setSegmentedFilesNum(BigDecimal.ZERO);
        batchFilesJobQueue.setCreateTime(LocalDateTime.now());

        // persist batch queue
        batchFilesJobQueueRepository.addNew(batchFilesJobQueue);

        /*
         * Step 2: Add file queue records
         */
        List<FileJobQueue> fileJobQueues = new ArrayList<>();
        for (FileDto fileDto : fileDtoList) {
            String sid = IdGenerator.getUid();
            FileJobQueue fileJobQueue = new FileJobQueue();
            fileJobQueue.setSid(sid);
            fileJobQueue.setBatchId(batchId);
            fileJobQueue.setOriginalFileName(fileDto.getOriginalFileName());
            fileJobQueue.setUploadedFileName(fileDto.getUploadedFileName());
            fileJobQueue.setFileJobStatus(new BigDecimal(FileJobStatus.READY_TO_ANALYZE.getId()));
            fileJobQueue.setCreateTime(DateUtil.date());

            fileJobQueues.add(fileJobQueue);
        }

        // persist file queues
        fileJobQueueRepository.saveBatch(fileJobQueues);

        return batchId;
    }

    @Override
    public BatchQueueDto getReadyToAnalyzeQueuesByBatchId(String batchId) {
        // get batch queue of files which are ready to analyze by batch id
        BigDecimal jobStatus = new BigDecimal(FileJobStatus.READY_TO_ANALYZE.getId());
        List<FileJobQueue> fileJobQueues = fileJobQueueRepository.listByBatchIdAndStatus(batchId, jobStatus);

        BatchQueueDto batchQueueDto = new BatchQueueDto();
        batchQueueDto.setBatchId(batchId);
        batchQueueDto.setFileQueues(fileJobQueues);

        return batchQueueDto;
    }

    @Override
    public List<BatchFilesJobQueue> listReadyToAnalyzeBatchQueues() {
        BigDecimal jobStatus = new BigDecimal(BatchJobStatus.READY_TO_ANALYZE.getId());

        return batchFilesJobQueueRepository.listByStatus(jobStatus);
    }

    @Override
    public void updateBatchQueueStatusById(String batchId, BatchJobStatus jobStatus) {
        // get batch queue by id
        BatchFilesJobQueue batchQueue = batchFilesJobQueueRepository.getBySid(batchId);

        if (batchQueue != null) {
            batchQueue.setBatchJobStatus(new BigDecimal(jobStatus.getId()));
            batchQueue.setLastUpdate(LocalDateTime.now());

            batchFilesJobQueueRepository.updateBySid(batchId, batchQueue);
        }
    }

    @Override
    public PageData<BatchQueueVo> listAllBatchQueuesByPage(int pageNumber, int pageSize) {
        PageData<BatchFilesJobQueue> batchQueuePageData = batchFilesJobQueueRepository.listByPage(pageNumber, pageSize);

        List<BatchFilesJobQueue> batchFilesJobQueueList = batchQueuePageData.getData();

        // convert to vo list
        List<BatchQueueVo> batchQueueVoList = BatchQueueConverter.toBatchQueueVos(batchFilesJobQueueList);

        PageData<BatchQueueVo> batchQueueVoPageData = new PageData<>();
        batchQueueVoPageData.setTotalCount(batchQueuePageData.getTotalCount());
        batchQueueVoPageData.setCurrentPage(batchQueuePageData.getCurrentPage());
        batchQueueVoPageData.setData(batchQueueVoList);

        return batchQueueVoPageData;
    }
}
