package org.springframework.uac.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author mayato
 * @since 2020-08-11
 */
@TableName("gz_log")
@FieldNameConstants(prefix = "")
@Data
public class GzLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private String userId;

    @TableField("user_name")
    private String userName;

    @TableField("event_category")
    private String eventCategory;

    @TableField("event_level")
    private Integer eventLevel;

    @TableField("event_type")
    private String eventType;

    @TableField("message")
    private String message;

    @TableField("exception_code")
    private Integer exceptionCode;

    @TableField("is_exception")
    private Boolean isException;
    
    @TableField("is_success")
    private Boolean isSuccess;

    @TableField("request_uri")
    private String requestUri;

    @TableField("originate")
    private Integer originate;

    @TableField("process_time")
    private Long processTime;

    @TableField("server_addr")
    private String serverAddr;

    @TableField("remote_addr")
    private String remoteAddr;

    @TableField("remote_host")
    private String remoteHost;

    @TableField("remote_port")
    private Integer remotePort;

    @TableField("content_type")
    private String contentType;

    @TableField("method_name")
    private String methodName;

    @TableField("method_parameter")
    private String methodParameter;

    @TableField("request_parameter")
    private String requestParameter;

    @TableField("method_return")
    private String methodReturn;

    @TableField("create_date")
    private LocalDateTime createDate;

}
