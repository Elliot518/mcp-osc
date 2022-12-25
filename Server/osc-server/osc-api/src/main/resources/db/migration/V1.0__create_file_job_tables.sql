-- batch_files_job_queue
DROP TABLE IF EXISTS batch_files_job_queue;

CREATE TABLE batch_files_job_queue
(
    sid varchar(50) not null COMMENT '主键ID(批次号)',
    complete_percentage decimal(8,2) DEFAULT NULL COMMENT '任务完成百分比',
    batch_job_status int(11) DEFAULT NULL COMMENT '批量任务状态(0:待解析 1:解析中 3:执行完毕)',
    files_num int(11) DEFAULT NULL COMMENT '文件数量',
    analyzed_files_num int(11) DEFAULT NULL COMMENT '已解析文件数量',
    segmented_files_num int(11) DEFAULT NULL COMMENT '已处理文件数量',
    create_time datetime DEFAULT NULL COMMENT '记录插入时间',
    last_update datetime DEFAULT NULL COMMENT '记录最后更新时间',
    PRIMARY KEY (sid)
);


-- file_job_queue
DROP TABLE IF EXISTS file_job_queue;

CREATE TABLE file_job_queue
(
    sid varchar(50) not null COMMENT '主键ID',
    batch_id varchar(50) not null COMMENT '批次号',
    original_file_name varchar(50) DEFAULT NULL COMMENT '原始上传文件名',
    uploaded_file_name varchar(50) DEFAULT NULL COMMENT '上传后的文件名',
    file_job_status int(11) DEFAULT NULL COMMENT '文件任务状态(0:待解析 1:解析中 2:待处理 3:处理中 4:处理完毕 9:解析失败)',
    create_time datetime DEFAULT NULL COMMENT '记录插入时间',
    last_update datetime DEFAULT NULL COMMENT '记录最后更新时间',
    PRIMARY KEY (sid)
);



