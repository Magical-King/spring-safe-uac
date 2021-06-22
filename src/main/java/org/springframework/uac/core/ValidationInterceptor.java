package org.springframework.uac.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.uac.utils.MessageSourceUtil;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author Sir.D
 */
@Slf4j
@RestControllerAdvice
public class ValidationInterceptor {

    /**
     * 所有的消息源都来自资源文件
     */
    @Autowired
    protected MessageSourceUtil source;

    @ExceptionHandler(Exception.class)
    public Message expection(Exception ex) {
        log.error("=========> {}" , ex.getMessage());
        ex.printStackTrace();
        if (ex instanceof BindException) {
            BindException e = (BindException) ex;
            return this.getMessage(e.getBindingResult().getAllErrors());
        }

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
            return this.getMessage(e.getBindingResult().getAllErrors());
        }

        if (ex instanceof SpringSafeException) {
            SpringSafeException e = (SpringSafeException) ex;
            return this.improve(e.get());
        }

        return this.improve(new Message(Status.EXPIRED));
    }

    /**
     * 返回消息给前端
     *
     * @param message
     * @return
     */
    protected Message improve(Message message) {
        if (null == message) {
            message = new Message(Status.PARAM_INVALID);
        }

        // 若消息中没有message, 那么就从MessageSource中获取对应code的消息内容
        if (null == message.getMessage()) {
            message.setMessage(this.getMessage(
                    String.valueOf(message.getCode())));
        }

        return message;
    }

    /**
     * 返回验证失败的第一个错误信息
     *
     * @param errors
     * @return
     */
    private Message getMessage(List<ObjectError> errors) {
        ObjectError error = errors.get(0);
        Object[] arguments = error.getArguments();
        if (arguments.length > 0) {
            Object[] dest = new Object[arguments.length - 1];
            System.arraycopy(arguments, 1, dest, 0, dest.length);
            arguments = dest;
        }
        String message = this.getMessage(error.getDefaultMessage(), arguments);
        return new Message(Status.PARAM_INVALID, message);
    }

    /**
     * 获取资源文件中的信息
     *
     * @param name
     * @param args
     * @return
     */
    private String getMessage(String name, Object... args) {
        return source.message(name, args);
    }
}
