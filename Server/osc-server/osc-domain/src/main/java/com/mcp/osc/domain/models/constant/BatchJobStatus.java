package com.mcp.osc.domain.models.constant;

import com.mcp.osc.infrastructure.constant.IStatusCode;
import com.mcp.osc.infrastructure.constant.StatusHelper;

/**
 * @author KG
 * @description 批量任务状态
 * @date Created in 2022年09月21日 9:21 AM
 * @modified_by
 */
public enum BatchJobStatus implements IStatusCode {
    /* 待解析 */
    READY_TO_ANALYZE(0, "待解析"),

    /* 解析中 */
    ANALYZING(1, "解析中"),

    /* 待处理 */
    READY_TO_SEGMENT(2, "待处理"),

    /* 处理中 */
    SEGMENTING(3, "处理中"),

    /* 处理完毕 */
    SEGMENTED(4, "处理完毕");

    private int id;
    private String status;

    private BatchJobStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public static String match(int code) {
        return StatusHelper.match(code, BatchJobStatus.values());
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







