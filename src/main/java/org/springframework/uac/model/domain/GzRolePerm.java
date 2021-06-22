package org.springframework.uac.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-07-29
 */
@TableName("gz_role_perm")
@Data
@FieldNameConstants(prefix = "")
public class GzRolePerm implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_perm_id", type = IdType.AUTO)
    private String rolePermId;

    @TableField("perm_id")
    private Integer permId;

    @TableField("role_id")
    private String roleId;
}
