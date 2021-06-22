package org.springframework.uac.model.dto.log;

import lombok.Data;
import org.springframework.uac.model.BaseEntity;

/**
 * @author mayato
 */
@Data
public class LogListDto extends BaseEntity {


    private Integer eventLevel;

    private String eventType;

    private String eventCategory;

    private Boolean isSuccess;

    private String startTime;

    private String endTime;
}
