package com.mcp.osc.domain.exception;

import com.mcp.osc.infrastructure.api.IErrorCode;

/**
 * @author KG
 * @description 业务状态码和信息
 * @date Created in 2022年09月21日 09:55 AM
 * @modified_by
 */
public enum BusinessResultCode implements IErrorCode {
    /**
     * 数据校验错误码
     */
    DATA_CHECK_EMPTY_USER_ID("1001", "用户ID不能为空"),

    /**
     * 上传创建文件错误码
     */
    UPLOAD_FILE_EXCEED_LIMIT("2000", "上传文件大小超出限制"),
    UPLOAD_CREATE_FILE_ERROR("2001", "上传创建文件错误"),
    READ_DOC_FILE_ERROR("2002", "读取doc文件错误"),
    READ_DOCX_FILE_ERROR("2003", "读取docx文件错误"),
    READ_WORD_FILE_FORMAT_ERROR("2004", "此文件不是word文件"),
    SAVE_BATCH_ANALYZED_CONTENTS_FAIL("2005", "批量保存解析内容失败"),
    UPLOAD_FILE_NAME_EMPTY("2006", "上传文件名为空"),
    UPLOAD_FILE_FORMAT_NOT_SUPPORTED("2007", "上传文件的格式不支持解析"),

    DB_OPERATION_ERROR("9990", "数据库操作异常");

    private String code;
    private String message;

    private BusinessResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}


