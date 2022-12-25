package com.mcp.osc.domain.models.dto;

import lombok.Data;

import java.util.List;

/**
 * @author KG
 * @description 批量上传传输对象
 * @date Created in 2022年09月21日 4:51 PM
 * @modified_by
 */
@Data
public class BatchUploadDto {
    private int filesNum;

    private List<FileDto> fileDtos;
}
