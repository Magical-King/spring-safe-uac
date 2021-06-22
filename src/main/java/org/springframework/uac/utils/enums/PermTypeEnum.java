package org.springframework.uac.utils.enums;

/** 权限大类
 * @author mayato
 */

public enum PermTypeEnum {
    //系统管理
    SYSTEM_PERM (1,"1"),
    //系统审计
    AUDIO_PERM (2,"2"),
    //系统审核
    REVIEW_PERM (3,"3"),

    BUSINESS_PERM (4, "4");

    private Integer id;


    private String roleType;


    PermTypeEnum(Integer id, String roleType) {
        this.id = id;
        this.roleType = roleType;
    }

    public Integer getId() {
        return id;
    }

    public String getRoleType() {
        return roleType;
    }
}
