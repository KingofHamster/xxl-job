package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.ATaskInfo;
import com.xxl.job.admin.core.model.ATaskTimeInterval;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ATaskInfoDao {
    public List<ATaskInfo> findAll();
    public ATaskInfo findAllByTaskId(@Param("taskId") int taskId);
}
