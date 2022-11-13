package com.xxl.job.admin.alarm;

import com.xxl.job.admin.XxlJobAdminApplication;
import com.xxl.job.admin.core.conf.XxlJobAdminConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@SpringBootTest(classes = XxlJobAdminApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailAlarmTest {
    
    @Test
    void emailTest() throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = XxlJobAdminConfig.getAdminConfig().getMailSender().createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(XxlJobAdminConfig.getAdminConfig().getEmailFrom(), "hamster");
        helper.setTo("453720489@qq.com");
        helper.setSubject("title");
        helper.setText("test email content", true);
        XxlJobAdminConfig.getAdminConfig().getMailSender().send(mimeMessage);
    }
}
