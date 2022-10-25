package com.xxl.job.admin.core.util;
import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinitionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.cronutils.model.CronType.QUARTZ;
import static com.cronutils.model.field.expression.FieldExpressionFactory.*;
import static com.cronutils.model.field.expression.FieldExpressionFactory.on;

public class CronUtils {
    private static Logger logger = LoggerFactory.getLogger(CronUtils.class);
    
    public static String convertFromFixedRateTaskToCRON(long interval, long beginTime, long endTime) {
        CronBuilder cronBuilder = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ))
                .withYear(always()).withDoM(always()).withMonth(always()).withDoW(questionMark()).withHour(always())
                .withMinute(always()).withSecond(every(on(0), 10));
        Cron quartzBuiltCronExpression = cronBuilder.instance();
        String quartzBuiltCronExpressionString = quartzBuiltCronExpression.asString();
        System.out.println(quartzBuiltCronExpressionString);
        return quartzBuiltCronExpressionString;
    }
}
