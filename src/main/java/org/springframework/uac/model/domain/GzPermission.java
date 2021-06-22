package org.springframework.uac.model.domain;

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
 * @author Sir.D
 * @since 2020-07-29
 */
@TableName("gz_permission")
@Data
@FieldNameConstants(prefix = "")
public class GzPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "perm_id")
    private Integer permId;

    @TableField("perm_type")
    private Integer permType;

    @TableField("perm_parent_id")
    private Integer permParentId;

    @TableField("perm_parent_ids")
    private String permParentIds;

    @TableField("perm_name")
    private String permName;

    @TableField("component")
    private String component;

    @TableField("iframe_url")
    private String iframeUrl;

    @TableField("perm_url_prefix")
    private String permUrlPrefix;

    @TableField("perm_url")
    private String permUrl;

    @TableField("perm_status")
    private Boolean permStatus;
    @TableField("perm_role_type")
    private String permRoleType;
    @TableField("perm_order")
    private Integer permOrder;

    @TableField("perm_css")
    private String permCss;

    @TableField("perm_ico")
    private String permIco;

    @TableField("perm_is_show")
    private Boolean permIsShow;

    @TableField("perm_create_by")
    private String permCreateBy;

    @TableField("perm_create_date")
    private LocalDateTime permCreateDate;

    @TableField("perm_update_by")
    private String permUpdateBy;

    @TableField("perm_update_date")
    private LocalDateTime permUpdateDate;

}
