package com.mcp.osc.domain.models.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author KG
 * @description 批量上传文件传输对象实体
 * @date Created in 2022年09月21日 1:58 PM
 * @modified_by
 */
@Data
public class UploadFilesDto {
    private MultipartFile[] files;

    //private String fileType;
}
