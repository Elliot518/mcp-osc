package com.mcp.osc.infrastructure.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcp.osc.infrastructure.dao.ITaskInfoDao;
import com.mcp.osc.infrastructure.entity.TaskInfo;
import com.mcp.osc.infrastructure.mapper.TaskInfoMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author KG
 * @since 2022/9/21
 */
@Repository
public class TaskInfoDaoImpl extends ServiceImpl<TaskInfoMapper, TaskInfo> implements ITaskInfoDao {}

