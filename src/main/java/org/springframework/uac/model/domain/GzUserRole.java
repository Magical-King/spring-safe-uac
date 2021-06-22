package org.springframework.uac.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@TableName("gz_user_role")
@FieldNameConstants(prefix = "")
@NoArgsConstructor
@AllArgsConstructor
public class GzUserRole implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId("user_role_id")
    private String userRoleId;

    @TableField("role_id")
    private String roleId;

    @TableField("user_id")
    private String userId;

}
