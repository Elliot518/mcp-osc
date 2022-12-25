package com.mcp.osc.application.workflow.impl;


import com.mcp.osc.application.workflow.IBatchUploadWorkflowService;
import com.mcp.osc.domain.exception.BusinessException;
import com.mcp.osc.domain.exception.BusinessResultCode;
import com.mcp.osc.domain.models.dto.BatchUploadDto;
import com.mcp.osc.domain.models.dto.FileDto;
import com.mcp.osc.domain.models.dto.UploadFilesDto;
import com.mcp.osc.domain.service.components.BaseUploadService;
import com.mcp.osc.domain.service.components.IBatchQueueService;
import com.mcp.osc.domain.util.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KG
 * @description 批量上传工作流服务实现
 * @date Created in 2022年09月22日 4:59 PM
 * @modified_by
 */
@Slf4j
@Service
public class BatchUploadWorkflowServiceImpl extends BaseUploadService implements IBatchUploadWorkflowService {

    @Autowired
    private IBatchQueueService batchQueueService;

    @Override
    public String execute(UploadFilesDto dto) {
        MultipartFile[] files = dto.getFiles();

        /*
            Step 1: 批量上传文件
        */
        List<FileDto> fileDtoList = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();

            // step 1-1: 验证文件名合法性
            MyFileUtils.validateUploadWcFile(fileName);

            // step 1-2: 检查限制文件大小
            long fileSize = file.getSize();
            int limitSize = uploadInfo.getLimitFileSize();
            String limitUnit = uploadInfo.getLimitFileUnit();

            if (!MyFileUtils.checkFileSizeLimit(fileSize, limitSize, limitUnit)) {
                log.error("##### 上传文件大小超出限制, 文件名: {} #####", fileName);
                String errMsg = String.format("%s - 文件名: %s", BusinessResultCode.UPLOAD_FILE_EXCEED_LIMIT.getMessage(), fileName);
                throw new BusinessException(BusinessResultCode.UPLOAD_FILE_EXCEED_LIMIT.getCode(), errMsg);
            }

            // 上传单个文件
            FileDto fileDto = uploadFileWithPath(file);

            fileDtoList.add(fileDto);
        }

        /*
            Step 2: 插入批量任务队列和文件子任务队列
         */
        BatchUploadDto batchUploadDto = new BatchUploadDto();
        batchUploadDto.setFileDtos(fileDtoList);
        batchUploadDto.setFilesNum(fileDtoList.size());

        return batchQueueService.addNew(batchUploadDto);
    }
}

