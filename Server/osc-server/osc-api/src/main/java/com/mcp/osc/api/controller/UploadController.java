package com.mcp.osc.api.controller;


import com.mcp.osc.application.workflow.IBatchUploadWorkflowService;
import com.mcp.osc.domain.models.dto.UploadFilesDto;
import com.mcp.osc.infrastructure.api.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author KG
 * @description 文件上传
 * @date Created in 2022年09月22日 9:36 AM
 * @modified_by
 */
@Slf4j
@Api(tags = "文件上传")
@RestController
@RequestMapping("/upload")
@ResponseResult
public class UploadController {

    @Autowired
    private IBatchUploadWorkflowService batchUploadWorkflowService;

    @ApiOperation(value = "批量文件上传")
    @ResponseBody
    @PostMapping(value = "/batch")
    public String uploadBatchFiles(@RequestPart("file") MultipartFile[] files) {

        UploadFilesDto dto = new UploadFilesDto();
        dto.setFiles(files);

        return batchUploadWorkflowService.execute(dto);
    }
}









