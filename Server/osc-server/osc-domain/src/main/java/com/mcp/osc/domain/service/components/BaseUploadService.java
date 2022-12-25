package com.mcp.osc.domain.service.components;

import com.mcp.osc.domain.models.dto.FileDto;
import com.mcp.osc.domain.models.setting.UploadInfo;
import com.mcp.osc.domain.util.MultipartFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author KG
 * @description
 * @date Created in 2022年09月22日 10:05 AM
 * @modified_by
 */
public class BaseUploadService {
    @Autowired
    protected UploadInfo uploadInfo;

    /**
     * 上传文件
     * @author KG
     * @date 2022/9/22 11:11 AM
     * @param file 上传文件
     * @return FileDto 文件对象
     */
    protected FileDto uploadFileWithPath(MultipartFile file) {
        // upload file, return file name
        String uploadedFileName = MultipartFileUtils.uploadFile(uploadInfo.getBaseFolder(), file);
        String originalFileName = file.getOriginalFilename();

        FileDto fileDto = new FileDto();
        fileDto.setUploadedFileName(uploadedFileName);
        fileDto.setOriginalFileName(originalFileName);

        return fileDto;
    }
}


