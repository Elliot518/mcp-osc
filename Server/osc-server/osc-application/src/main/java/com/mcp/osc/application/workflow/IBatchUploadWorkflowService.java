package com.mcp.osc.application.workflow;


import com.mcp.osc.domain.models.dto.UploadFilesDto;

/**
 * @author KG
 * @description 批量上传工作流服务接口
 * @date Created in 2022年09月21日 4:57 PM
 * @modified_by
 */
public interface IBatchUploadWorkflowService {
    /***
     * 批量上传
     * @author KG
     * @date 2021/9/16 1:53 PM
     * @param dto 批量上传请求
     * @return java.lang.String 批次号
     */
    String execute(UploadFilesDto dto);
}



