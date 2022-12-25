package com.mcp.osc.api.controller;

import com.mcp.osc.domain.models.vo.BatchQueueVo;
import com.mcp.osc.domain.service.components.IBatchQueueService;
import com.mcp.osc.infrastructure.api.ResponseResult;
import com.mcp.osc.infrastructure.data.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author KG
 * @description 任务队列
 * @date Created in 2021年09月22日 3:53 PM
 * @modified_by
 */
@Slf4j
@Api(tags = "任务队列")
@RestController
@RequestMapping("/queue")
@ResponseResult
public class QueueController {

    @Autowired
    private IBatchQueueService batchQueueService;

    //@Autowired
    //private IFileQueueService fileQueueService;

    @ApiOperation(value = "分页显示批量任务队列")
    @ResponseBody
    @GetMapping(value = "/batches")
    public PageData<BatchQueueVo> listBatchQueuesByPage(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize) {
        return batchQueueService.listAllBatchQueuesByPage(pageNumber, pageSize);
    }

    /*@ApiOperation(value = "根据批次号显示文件队列")
    @ResponseBody
    @GetMapping(value = "/batches/{batchId}/files")
    public List<FileQueueVo> listFileQueuesByBatchId(@PathVariable String batchId) {
        return fileQueueService.listFileQueuesByBatchId(batchId);
    }*/
}







