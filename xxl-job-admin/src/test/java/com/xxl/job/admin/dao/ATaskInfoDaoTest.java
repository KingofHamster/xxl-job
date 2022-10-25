package com.xxl.job.admin.dao;


import com.xxl.job.admin.core.model.ATaskInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ATaskInfoDaoTest {
    @Resource
    ATaskInfoDao aTaskInfoDao;

    @Test
    public void test() {
//        List<ATaskInfo> tasks =  aTaskInfoDao.findAll();
//        for(ATaskInfo taskInfo : tasks) {
//            System.out.println(taskInfo.toString());
//        }

        ATaskInfo task = aTaskInfoDao.findAllByTaskId(17876);
        System.out.println(task);
        
    }
}
