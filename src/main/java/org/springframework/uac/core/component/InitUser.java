package org.springframework.uac.core.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.uac.model.domain.GzUser;
import org.springframework.uac.model.domain.GzUserRole;
import org.springframework.uac.service.role.GzUserRoleService;
import org.springframework.uac.service.user.GzUserBackService;
import org.springframework.uac.service.user.GzUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sir.D
 */
@Configuration
public class InitUser {

    @Autowired
    private GzUserService userService;

    @Autowired
    private GzUserBackService userBackService;

    @Autowired
    private GzUserRoleService userRoleService;

    public void init() {
        Map<String, GzUser> user = createUser();
        List<String> initUser = this.userService.findInitUser();
        for (String userId : initUser) {
            user.remove(userId);
        }

        List<GzUser> userList = new ArrayList<>();
        user.forEach((k,v) -> userList.add(v));

        if (null != userList) {
            this.userService.saveBatch(userList);
        }

        List<GzUserRole> userRole = createUserRole();
        this.userRoleService.delete();
        this.userRoleService.saveBatch(userRole);
    }


    private static List<GzUserRole> createUserRole() {
        List<GzUserRole> list = new ArrayList<>();
        GzUserRole userRole = new GzUserRole("1", "sys_admin", "sys_admin");
        list.add(userRole);
        userRole = new GzUserRole("2", "sys_audit", "sys_audit");
        list.add(userRole);
        userRole = new GzUserRole("3", "sys_review", "sys_review");
        list.add(userRole);
        userRole = new GzUserRole("4", "operator", "operator");
        list.add(userRole);
        return list;
    }

    private static Map<String, GzUser> createUser() {
        Map<String, GzUser> map = new HashMap<>(16);

        String password = "8B109EE12D94E0CFAC9DE8BE072D46A680434E688E9FEB1E2F4E34B6FA62E56D";
        LocalDateTime now = LocalDateTime.now();

        GzUser system = new GzUser();
        system.setUserId("sys_admin");
        system.setUserOrgId("system");
        system.setUserName("系统管理员");
        system.encodedUserPassword(password);
        system.setUserStatus(0);
        system.setUserEmail("admin@gzrobot.com");
        system.setUserIsTemporary(false);
        system.setUserCreateBy("system");
        system.setUserCreateDate(now);
        system.setUserUpdateBy("system");
        system.setUserUpdateDate(now);
        system.setUserSalt(system.generateUserSalt());
        map.put("sys_admin",system);


        system = new GzUser();
        system.setUserId("sys_audit");
        system.setUserOrgId("system");
        system.setUserName("系统审计员");
        system.encodedUserPassword(password);
        system.setUserStatus(0);
        system.setUserEmail("audit@gzrobot.com");
        system.setUserIsTemporary(false);
        system.setUserCreateBy("system");
        system.setUserCreateDate(now);
        system.setUserUpdateBy("system");
        system.setUserUpdateDate(now);
        system.setUserSalt(system.generateUserSalt());
        map.put("sys_audit",system);

        system = new GzUser();
        system.setUserId("sys_review");
        system.setUserOrgId("system");
        system.setUserName("系统审核员");
        system.encodedUserPassword(password);
        system.setUserStatus(0);
        system.setUserEmail("review@gzrobot.com");
        system.setUserIsTemporary(false);
        system.setUserCreateBy("system");
        system.setUserCreateDate(now);
        system.setUserUpdateBy("system");
        system.setUserUpdateDate(now);
        system.setUserSalt(system.generateUserSalt());
        map.put("sys_review",system);

        system = new GzUser();
        system.setUserId("operator");
        system.setUserOrgId("system");
        system.setUserName("业务操作员");
        system.encodedUserPassword(password);
        system.setUserStatus(0);
        system.setUserEmail("operator@gzrobot.com");
        system.setUserIsTemporary(false);
        system.setUserCreateBy("system");
        system.setUserCreateDate(now);
        system.setUserUpdateBy("system");
        system.setUserUpdateDate(now);
        system.setUserSalt(system.generateUserSalt());
        map.put("operator",system);

        return map;
    }


}
