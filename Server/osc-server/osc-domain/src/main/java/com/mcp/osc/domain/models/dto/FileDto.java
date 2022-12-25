package com.mcp.osc.domain.models.dto;

import lombok.Data;

/**
 * @author KG
 * @description 上传文件传输对象(包含原始文件名和上传后的文件名)
 * @date Created in 2022年09月21日 3:55 PM
 * @modified_by
 */
@Data
public class FileDto {
    /**
     * 原始上传文件名
     */
    private String originalFileName;

    /**
     * 上传后的文件名
     */
    private String uploadedFileName;
}
