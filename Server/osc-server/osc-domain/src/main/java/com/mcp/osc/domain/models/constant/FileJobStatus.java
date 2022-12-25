package com.mcp.osc.domain.models.constant;

import com.mcp.osc.infrastructure.constant.IStatusCode;
import com.mcp.osc.infrastructure.constant.StatusHelper;

/**
 * @author KG
 * @description 文件任务状态
 * @date Created in 2022年09月21日 9:21 PM
 * @modified_by
 */
public enum FileJobStatus implements IStatusCode {
    /* 待解析 */
    READY_TO_ANALYZE(0, "待解析"),

    /* 解析中 */
    ANALYZING(1, "解析中"),

    /* 待处理 */
    READY_TO_SEGMENT(2, "待处理"),

    /* 处理中 */
    SEGMENTING(3, "处理中"),

    /* 处理完毕 */
    SEGMENTED(4, "处理完毕"),

    /* 解析失败 */
    ANALYZE_FAIL(9, "解析失败"),

    /* 处理失败 */
    SEGMENT_FAIL(10, "处理失败");

    private final int id;
    private final String status;

    private FileJobStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public static String match(int code) {
        return StatusHelper.match(code, FileJobStatus.values());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getStatus() {
        return status;
    }
}




