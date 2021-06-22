package org.springframework.uac.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-28
 */
@Data
@TableName("gz_security_config")
@FieldNameConstants(prefix = "")
public class GzSecurityConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("config_id")
    private String configId;

    @TableField("config_name")
    private String configName;

    @TableField("config_key")
    private Boolean configKey;

    @TableField("config_value")
    private String configValue;

    @TableField("is_sys")
    private Boolean isSys;

    @TableField("create_by")
    private String createBy;

    @TableField("create_date")
    private LocalDateTime createDate;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_date")
    private LocalDateTime updateDate;

    @TableField("remake")
    private String remake;

}
