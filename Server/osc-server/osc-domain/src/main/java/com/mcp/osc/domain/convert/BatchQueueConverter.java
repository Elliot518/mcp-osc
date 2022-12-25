package com.mcp.osc.domain.convert;

import com.mcp.osc.domain.models.constant.BatchJobStatus;
import com.mcp.osc.domain.models.vo.BatchQueueVo;
import com.mcp.osc.infrastructure.entity.BatchFilesJobQueue;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KG
 * @description 批量队列对象转换器
 * @date Created in 2022年09月22日 2:12 PM
 * @modified_by
 */
public class BatchQueueConverter {
    /**
     * 集合转换
     * @param source 实体类
     * @return java.util.List<BatchQueueVo>
     * @author KG
     * @date 2022/9/22 3:24 PM
     */
    public static List<BatchQueueVo> toBatchQueueVos(List<BatchFilesJobQueue> source) {
        List<BatchQueueVo> batchQueueVoList = new ArrayList<>();
        for (BatchFilesJobQueue bfq : source) {
            BatchQueueVo batchQueueVo = new BatchQueueVo();
            BeanUtils.copyProperties(source, batchQueueVo);
            batchQueueVo.setBatchId(bfq.getSid());
            batchQueueVo.setStatus(BatchJobStatus.match(bfq.getBatchJobStatus().intValue()));
            batchQueueVoList.add(batchQueueVo);
        }

        return batchQueueVoList;
    }
}
