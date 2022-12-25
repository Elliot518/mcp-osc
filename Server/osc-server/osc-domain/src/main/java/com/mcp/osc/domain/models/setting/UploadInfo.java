package com.mcp.osc.domain.models.setting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author KG
 * @description 上传信息
 * @date Created in 2022年09月22日 10:55 AM
 * @modified_by
 */
@Component
public class UploadInfo {
    @Value("${upload.baseFolder}")
    private String baseFolder;

    @Value("${upload.realFolder}")
    private String realFolder;

    @Value("${upload.limitFileSize}")
    private String limitFileSize;

    @Value("${upload.limitFileUnit}")
    private String limitFileUnit;

    public String getBaseFolder() {
        return baseFolder;
    }

    public void setBaseFolder(String baseFolder) {
        this.baseFolder = baseFolder;
    }

    public String getRealFolder() {
        return realFolder;
    }

    public void setRealFolder(String realFolder) {
        this.realFolder = realFolder;
    }

    public int getLimitFileSize() {
        return Integer.parseInt(limitFileSize);
    }

    public void setLimitFileSize(String limitFileSize) {
        this.limitFileSize = limitFileSize;
    }

    public String getLimitFileUnit() {
        return limitFileUnit;
    }

    public void setLimitFileUnit(String limitFileUnit) {
        this.limitFileUnit = limitFileUnit;
    }
}



