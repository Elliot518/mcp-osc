package com.mcp.osc.domain.util;

import com.mcp.osc.domain.exception.BusinessException;
import com.mcp.osc.domain.exception.BusinessResultCode;
import com.mcp.osc.domain.models.constant.IFileExtensionConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: KG
 * @description: 文件工具
 * @date: Created in 2022年09月21日 9:27 PM
 * @modified by:
 */
@Slf4j
public class MyFileUtils {

    private static final String UNIT_B = "B";
    private static final String UNIT_K = "K";
    private static final String UNIT_M = "M";
    private static final String UNIT_G = "G";

    /**
     * 判断文件大小限制
     * @author KG
     * @date 2022/9/21 9:34 PM
     * @param len 文件长度
     * @param size 限制大小
     * @param unit 限制单位(B,K,M,G)
     * @return boolean
     */
    public static boolean checkFileSizeLimit(Long len, int size, String unit) {
        double fileSize = 0;

        if (UNIT_B.equalsIgnoreCase(unit)) {
            fileSize = len;
            log.info("***check file size: {} B", fileSize);
        } else if (UNIT_K.equalsIgnoreCase(unit)) {
            fileSize = (double) len / 1024;
            log.info("***check file size: {} K", fileSize);
        } else if (UNIT_M.equalsIgnoreCase(unit)) {
            fileSize = (double) len / 1048576;
            log.info("***check file size: {} M", fileSize);
        } else if (UNIT_G.equalsIgnoreCase(unit)) {
            fileSize = (double) len / 1073741824;
            log.info("***check file size: {} G", fileSize);
        }

        return fileSize <= size;
    }

    /**
     * 检查上传的词云文件合法性
     * @author KG
     * @date 2022/9/21 11:42 AM
     * @param fileName 文件名
     */
    public static void validateUploadWcFile(String fileName) throws BusinessException {
        if (StringUtils.isEmpty(fileName)) {
            log.error("##### 上传文件名为空, 文件名: {} #####", fileName);
            throw new BusinessException(BusinessResultCode.UPLOAD_FILE_NAME_EMPTY);
        }

        if (!fileName.endsWith(IFileExtensionConst.XLSX)) {
            log.error("##### 上传文件格式不支持, 文件名: {} #####", fileName);
            throw new BusinessException(BusinessResultCode.UPLOAD_FILE_FORMAT_NOT_SUPPORTED);
        }
    }
}


