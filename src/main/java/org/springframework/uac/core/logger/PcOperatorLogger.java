package org.springframework.uac.core.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.logger.OperatorLog;
import org.springframework.logger.service.OperatorLogger;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.stereotype.Component;
import org.springframework.uac.core.security.SecurityUser;
import org.springframework.uac.core.security.SecurityUtils;
import org.springframework.uac.model.domain.GzLog;
import org.springframework.uac.service.log.GzLogService;
import org.springframework.uac.utils.MessageSourceUtil;

/**
 * @author Sir.D
 */
@Component
public class PcOperatorLogger implements OperatorLogger {
    @Autowired
    private MessageSourceUtil source;
    @Autowired
    private GzLogService gzLogService;

    @Override
    public void save(OperatorLog operatorLog) {
        String userId = "";
        String userName = "";
        String exceptionMessage = "";
        String message = "";
        GzLog gzLog = operatorLogToGzLog(operatorLog);
        //获取模块类型
        String eventCategory = source.message(operatorLog.getEventCategory());

        if (!eventCategory.isEmpty()) {
            gzLog.setEventCategory(eventCategory);
        }

        SecurityUser user = SecurityUtils.getCurrentUser();
        if (user != null) {
            userId = user.getUserId();
            userName = user.getUserName();
            message = "用户【" + userId + "】,执行了" ;
        }
        gzLog.setUserId(userId);
        gzLog.setUserName(userName);
        Exception exception = operatorLog.getException();

        if (exception instanceof SpringSafeException) {
            int code = ((SpringSafeException) exception).get().getCode();
            gzLog.setExceptionCode(code);
            //获取异常类型
            String message1 = source.message(code);
            if (!message1.isEmpty()) {
                exceptionMessage = "," + message1;
            }
        }

        message =  message + "【" + gzLog.getEventCategory() + "】模块中" + operatorLog.getMessage() + exceptionMessage;
        gzLog.setMessage(message);

        gzLogService.save(gzLog);
    }

    private GzLog operatorLogToGzLog(OperatorLog operatorLog){
        GzLog gzLog = new GzLog();
        gzLog.setUserId(operatorLog.getUserId());
        gzLog.setUserName(operatorLog.getUserName());
        gzLog.setEventLevel(operatorLog.getEventLevel());
        gzLog.setEventCategory(operatorLog.getEventCategory());
        gzLog.setEventType(operatorLog.getEventType());
        gzLog.setMessage(operatorLog.getMessage());
        gzLog.setExceptionCode(operatorLog.getExceptionCode());
        gzLog.setIsException(operatorLog.getIsException());
        gzLog.setIsSuccess(operatorLog.getIsSuccess());
        gzLog.setRequestUri(operatorLog.getRequestURI());
        gzLog.setOriginate(operatorLog.getOriginate());
        gzLog.setServerAddr(operatorLog.getServerAddr());
        gzLog.setRemoteAddr(operatorLog.getRemoteAddr());
        gzLog.setRemoteHost(operatorLog.getRemoteHost());
        gzLog.setRemotePort(operatorLog.getRemotePort());
        gzLog.setContentType(operatorLog.getContentType());
        gzLog.setMethodName(operatorLog.getMethodName());
        gzLog.setMethodParameter(operatorLog.getMethodParameter());
        gzLog.setRequestParameter(operatorLog.getRequestParameter());
        gzLog.setMethodReturn(operatorLog.getMethodReturn());
        gzLog.setCreateDate(operatorLog.getCreateDate());
        gzLog.setProcessTime(operatorLog.getProcessTime());
        return gzLog;
    }
}
