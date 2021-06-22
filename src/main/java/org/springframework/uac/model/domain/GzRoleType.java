package org.springframework.uac.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-29
 */
@TableName("gz_role_type")
@Data
@FieldNameConstants(prefix = "")
public class GzRoleType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(value = "role_id")
    private String roleId;

    @TableId(value = "role_type_id")
    private Integer roleTypeId;

    @TableField("role_type_name")
    private String roleTypeName;

    @TableField("role_type_leavel")
    private Integer roleTypeLeavel;

}
