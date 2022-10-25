package com.xxl.job.fixrate;

import com.xxl.job.admin.XxlJobAdminApplication;
import com.xxl.job.admin.core.fixrate.MultiFixRateConf;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest(classes = XxlJobAdminApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MultiFixRateConfTest {
    
    @Test
    public void test(){
        MultiFixRateConf multiFixRateConf = new MultiFixRateConf("34200 43200 1800 4 1", "\\|");
        Date lastTime = new Date();
        
        for (int i = 0; i < 500; i++) {
            lastTime = multiFixRateConf.getEarliestNextValidTimeAfter(lastTime);
            System.out.println(lastTime);
        }
    }
}
