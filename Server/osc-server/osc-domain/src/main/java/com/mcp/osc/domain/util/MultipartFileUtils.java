package com.mcp.osc.domain.util;

import com.mcp.osc.domain.exception.BusinessException;
import com.mcp.osc.domain.exception.BusinessResultCode;
import com.mcp.osc.infrastructure.generator.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author KG
 * @description
 * @date Created in 2021年08月23日 10:26 PM
 * @modified_by
 */
@Slf4j
public class MultipartFileUtils {
    /**
     * 上传文件
     * @author KG
     * @date 2022/9/21 10:50 AM
     * @param file 文件
     * @return java.lang.String 文件名
     */
    public static String uploadFile(String baseFolder, MultipartFile file) {
        // 服务器上的文件名
        String filename = format(Objects.requireNonNull(file.getOriginalFilename()));

        //String path = System.getProperty("user.dir") + UPLOAD_FOLDER + filename;  //linux不能用\\
        String path = baseFolder + filename;
        log.info("upload path:" + path);

        File newFile = new File(path);

        try {
////            if (!newFile.createNewFile()) {
////                log.error("创建文件失败...");
////            }

            // 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
            file.transferTo(newFile);
        } catch (IOException e) {
            throw new BusinessException(BusinessResultCode.UPLOAD_CREATE_FILE_ERROR);
        }

        // return file url
        return filename;
    }

    private static String format(String oldFilename) {
        String[] str = oldFilename.split("\\.");

        return IdGenerator.getUid() + "." + str[1];
    }
}

