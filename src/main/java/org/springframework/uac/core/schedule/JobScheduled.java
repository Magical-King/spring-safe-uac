package org.springframework.uac.core.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.DateUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.uac.model.domain.GzLog;
import org.springframework.uac.service.GzSecurityConfigService;
import org.springframework.uac.service.email.GzMailService;
import org.springframework.uac.service.log.GzLogService;
import org.springframework.uac.utils.ComputerMonitorUtil;
import org.springframework.uac.utils.EmailUtil;
import org.springframework.uac.utils.enums.EmailSubject;

import java.util.Date;

/**
 * @author Sir.D
 */
@Slf4j
@Configuration
@EnableScheduling
public class JobScheduled {

    @Autowired
    private GzSecurityConfigService configService;

    @Autowired
    private GzMailService mailService;

    @Autowired
    private GzLogService logService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void monitorSystemResource() throws SpringSafeException {
        boolean sendEmailFlag = this.configService.sendEmailFlag();
        int var1 = this.configService.resourceLimitPercent();
        log.info("===> 系统资源监控: 当前时间为 {}", DateUtil.getTimeString());

        double var2 = ComputerMonitorUtil.getCpuUsage();
        double var4 = ComputerMonitorUtil.getMemUsage();
        double var6 = 0.0D;

        try {
            var6 = ComputerMonitorUtil.getDiskUsage();
        } catch (Exception var13) {
            var13.printStackTrace();
        }

        String var8;
        if (var2 > (double)var1) {
            log.warn("===> 系统资源使用率告警：当前【cpu】占用率`{}%`,超过系统资源告警门限：`{}%`", var2, var1);
            var8 = "【系统】执行了【系统资源管理】中检查系统【CPU】占用率的操作" + ",当前【CPU】占用率为" + var2 + "%,已超过系统配置的资源告警门限" + var1 + "%";
            saveLog(var8);

            if (sendEmailFlag) {
                String text = EmailUtil.text(null, "system", var8, new Date());
                this.mailService.sendAndSave("admin@gzrobot.com", EmailSubject.SYSTEM_RESOURCE_MONITOR_ALARM.code(),text);
            }
        }

        if (var4 > (double)var1) {
            log.warn("===> 系统资源使用率告警：当前【内存】占用率`{}%`,超过系统资源告警门限：`{}%`", var4, var1);
            var8 = "【系统】执行了【系统资源管理】中检查系统内存占用率的操作" + ",当前内存占用率为" + var4 + "%,已超过系统配置的资源告警门限" + var1 + "%";
            saveLog(var8);

            if (sendEmailFlag) {
                String text = EmailUtil.text(null, "system", var8, new Date());
                this.mailService.sendAndSave("admin@gzrobot.com", EmailSubject.SYSTEM_RESOURCE_MONITOR_ALARM.code(),text);
            }
        }

        if (var6 > (double)var1) {
            log.warn("===> 系统资源使用率告警：当前【磁盘】占用率`{}%`,超过系统资源告警门限：`{}%`", var6, var1);
            var8 = "【系统】执行了【系统资源管理】中检查系统【磁盘】占用率的操作" + ",当前【磁盘】占用率为" + var6 + "%,已超过系统配置的资源告警门限" + var1 + "%";
            saveLog(var8);

            if (sendEmailFlag) {
                String text = EmailUtil.text(null, "system", var8, new Date());
                this.mailService.sendAndSave("admin@gzrobot.com", EmailSubject.SYSTEM_RESOURCE_MONITOR_ALARM.code(),text);
            }
        }
    }

    private void saveLog(String message) {
        GzLog log = new GzLog();
        log.setUserId("system");
        log.setUserName("system");
        log.setEventCategory("系统资源管理");
        log.setEventLevel(2);
        log.setEventType("资源管理");
        log.setMessage(message);
        log.setIsException(false);
        log.setIsSuccess(true);
        log.setOriginate(3);
        this.logService.save(log);
    }
}
