package com.xxl.job.admin.dbmigration;


import com.xxl.job.admin.core.fixrate.dayhelper.DayHelper;
import com.xxl.job.admin.core.model.ATaskInfo;
import com.xxl.job.admin.core.model.ATaskTimeInterval;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.route.ExecutorRouteStrategyEnum;
import com.xxl.job.admin.core.scheduler.MisfireStrategyEnum;
import com.xxl.job.admin.core.scheduler.ScheduleTypeEnum;
import com.xxl.job.admin.core.util.TimeConstants;
import com.xxl.job.admin.dao.ATaskInfoDao;
import com.xxl.job.admin.dao.ATaskTimeIntervalDao;
import com.xxl.job.admin.dao.XxlJobInfoDao;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskMigration {

    @Resource
    ATaskInfoDao aTaskInfoDao;

    @Resource
    ATaskTimeIntervalDao aTaskTimeIntervalDao;

    @Resource
    XxlJobInfoDao xxlJobInfoDao;

    @Test
    public void migrateAll() {
        List<ATaskInfo> tasks = aTaskInfoDao.findAll();
        for (ATaskInfo task : tasks) {
            migrateOneTask(task);
        }
    }

    @Test
    public void migrateOne() {
        ATaskInfo aTaskInfo = aTaskInfoDao.findAllByTaskId(14963);
        migrateOneTask(aTaskInfo);
    }

    public void migrateOneTask(ATaskInfo aTaskInfo) {
        List<ATaskTimeInterval> aTaskTimeIntervalList = aTaskTimeIntervalDao.findAllByTaskId(aTaskInfo.getTaskId());
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setAddTime(new Date());
        xxlJobInfo.setAlarmEmail("AlarmEmail");
        xxlJobInfo.setId(aTaskInfo.getTaskId());
        xxlJobInfo.setAuthor(aTaskInfo.getBusType());
        xxlJobInfo.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION.name());
        xxlJobInfo.setExecutorFailRetryCount(aTaskInfo.getBusFailHanding());
        xxlJobInfo.setExecutorHandler(aTaskInfo.getBusKey());
        xxlJobInfo.setExecutorParam(aTaskInfo.getBusRunParam());
        xxlJobInfo.setExecutorRouteStrategy(ExecutorRouteStrategyEnum.FIRST.name());
        xxlJobInfo.setExecutorTimeout(aTaskInfo.getBusTimeout() / 1000);

        xxlJobInfo.setGlueUpdatetime(new Date());
        xxlJobInfo.setGlueRemark("迁移任务" + aTaskInfo.getTaskId());
        
        
        xxlJobInfo.setUpdateTime(new Date());
        xxlJobInfo.setTriggerLastTime(0);
        xxlJobInfo.setTriggerNextTime(0);
        xxlJobInfo.setTriggerStatus(0);

        xxlJobInfo.setGlueType(GlueTypeEnum.BEAN.name());
        xxlJobInfo.setJobDesc(aTaskInfo.getRemark());

        xxlJobInfo.setScheduleType(ScheduleTypeEnum.FIX_RATE.name());
        xxlJobInfo.setJobGroup(1);
        xxlJobInfo.setMisfireStrategy(MisfireStrategyEnum.DO_NOTHING.name());

        StringBuilder sb = new StringBuilder();
        for (ATaskTimeInterval aTaskTimeInterval : aTaskTimeIntervalList) {
            int beginTime = parseATaskInfoTime(aTaskTimeInterval.getBegintime());
            int endTime = parseATaskInfoTime(aTaskTimeInterval.getEndtime());
            int interval = aTaskTimeInterval.getInterval() / 1000;
            // 如果interval是0表示只运行一次，因此设置为24小时，保证会超过今天;
            interval = interval <= 0 ? 24 * 60 * 60 : 0;
            int actionType = aTaskTimeInterval.getActiontype();
            String actionDays = aTaskTimeInterval.getActiondays();
            if (actionDays == null || actionDays.equals("0")) actionDays = "";
            String singleFixConfStr = String.format("%d %d %d %s %s", beginTime, endTime, interval, actionType, actionDays);
            sb.append("|").append(singleFixConfStr.trim());
        }
        sb.deleteCharAt(0);
        xxlJobInfo.setScheduleConf(sb.toString());
        System.out.println(xxlJobInfo);
        xxlJobInfoDao.save(xxlJobInfo);
        if (aTaskInfo.getBusInitKey() != null) {
            XxlJobInfo childJob;
            try {
                childJob = xxlJobInfo.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            childJob.setJobDesc(xxlJobInfo.getId()+"的子任务");
            xxlJobInfoDao.save(childJob);
            xxlJobInfo.setExecutorHandler(aTaskInfo.getBusInitKey());
            xxlJobInfo.setChildJobId(String.valueOf(childJob.getId()));
            xxlJobInfoDao.update(xxlJobInfo);
        }
//        if (aTaskInfo.getBusInitKey())
    }

    public static int parseATaskInfoTime(int time) {
        StringBuilder timeStr = new StringBuilder(String.valueOf(time));
        while (timeStr.length() < 6) {
            timeStr.insert(0, "0");
        }
        return TimeConstants.toSeconds(Integer.parseInt(timeStr.substring(0, 2)),
                Integer.parseInt(timeStr.substring(2, 4)),
                Integer.parseInt(timeStr.substring(4, 6)));
    }
    
}
