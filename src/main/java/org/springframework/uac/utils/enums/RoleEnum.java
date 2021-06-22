package org.springframework.uac.utils.enums;

/**
 * @author mayato
 */
public enum  RoleEnum {
    //系统管理员
    SYS_ADMIN("sys_admin",1,"系统管理员",0),
    //系统审计员
    SYS_AUDIT("sys_audit",2,"系统审计员",0),
    //系统审核员
    SYS_REVIEW("sys_review",3,"系统审核员",0),
    //业务操作员
    OPERATOR("operator",4,"业务操作员",1);


    private String roleId;

    private Integer roleTypeId;

    private String roleName;

    private Integer level;

    RoleEnum(String roleId, Integer roleTypeId, String roleName,Integer level) {
        this.roleId = roleId;
        this.roleTypeId = roleTypeId;
        this.roleName = roleName;
        this.level=level;
    }

    public String getRoleId() {
        return roleId;
    }

    public Integer getRoleTypeId() {
        return roleTypeId;
    }

    public String getRoleName() {
        return roleName;
    }

    public Integer getLevel() {
        return level;
    }
}
