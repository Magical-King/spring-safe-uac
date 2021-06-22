package org.springframework.uac.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.uac.model.BaseEntity;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-29
 */
@TableName("gz_role")
@Data
@FieldNameConstants(prefix = "")
public class GzRole extends BaseEntity {

    private static final long serialVersionUID = 1L;


    @TableId(value="role_id")
    private String roleId;

    @TableField("role_org_id")
    private String roleOrgId;

    @TableField("role_type_id")
    private Integer roleTypeId;

    @TableField("role_name")
    private String roleName;

    @TableField("role_status")
    private Integer roleStatus;

    @TableField("role_create_by")
    private String roleCreateBy;

    @TableField("role_create_date")
    private LocalDateTime roleCreateDate;

    @TableField("role_update_by")
    private String roleUpdateBy;

    @TableField("role_update_date")
    private LocalDateTime roleUpdateDate;

    @TableField("role_remarks")
    private String roleRemarks;

}
