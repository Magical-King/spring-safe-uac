package org.springframework.uac.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.uac.model.BaseEntity;
import org.springframework.uac.model.validate.Create;
import org.springframework.uac.model.validate.Update;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-30
 */
@Data
@TableName("gz_organization")
@FieldNameConstants(prefix = "")
public class GzOrganization extends BaseEntity {

    @TableId("org_id")
    @NotBlank(groups = {Create.class, Update.class}, message = "organization.orgId.notnull")
    private String orgId;

    @TableField("org_parent_id")
    private String orgParentId;

    @TableField("org_code")
    private String orgCode;

    @TableField("org_type")
    private String orgType;

    @TableField("org_name")
    @NotBlank(groups = {Create.class}, message = "organization.orgName.notnull")
    private String orgName;

    @TableField("org_tel")
    private String orgTel;

    @TableField("org_fax")
    private String orgFax;

    @TableField("org_postcode")
    private String orgPostcode;

    @TableField("org_email")
    private String orgEmail;

    @TableField("org_address")
    private String orgAddress;

    @TableField("org_website")
    private String orgWebsite;

    @TableField("org_description")
    private String orgDescription;

    @TableField("org_is_invalid")
    private Boolean orgIsInvalid;

}
