package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.ATaskTimeInterval;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ATaskTimeIntervalDaoTest {
    
    @Resource
    ATaskTimeIntervalDao aTaskTimeIntervalDao;
    
    @Test
    public void findAllTest() {
        List<ATaskTimeInterval> timeIntervalList = aTaskTimeIntervalDao.findAll();
    }
    
    @Test
    public void findAllByIdTest() {
        List<ATaskTimeInterval> timeIntervalList = aTaskTimeIntervalDao.findAllByTaskId(565);
        for (ATaskTimeInterval aTaskTimeInterval : timeIntervalList) {
            System.out.println(aTaskTimeInterval.toString());
        }
    }
}
