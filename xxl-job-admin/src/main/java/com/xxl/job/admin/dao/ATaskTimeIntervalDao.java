package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.ATaskTimeInterval;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ATaskTimeIntervalDao {
    
    public List<ATaskTimeInterval> findAll();
    
    public List<ATaskTimeInterval> findAllByTaskId(@Param("taskId") int taskId);
}
