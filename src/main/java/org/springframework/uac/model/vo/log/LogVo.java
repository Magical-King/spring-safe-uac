package org.springframework.uac.model.vo.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author mayato
 */
@Data
public class LogVo {

    private String userId;

    private String userName;

    private String eventCategory;

    private Integer eventlevel;

    private String eventType;

    private String message;

    private Boolean isSuccess;

    private String remoteAddr;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}
