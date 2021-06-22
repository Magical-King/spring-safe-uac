package org.springframework.uac.core.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.mybatis.QWrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.uac.model.domain.GzRole;
import org.springframework.uac.model.domain.GzRoleType;
import org.springframework.uac.service.perm.GzPermissionService;
import org.springframework.uac.service.perm.GzRolePermService;
import org.springframework.uac.service.role.GzRoleService;
import org.springframework.uac.service.role.GzRoleTypeService;
import org.springframework.uac.utils.enums.RoleEnum;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author mayato
 */
@Configuration
public class InitRole {


    @Autowired
    GzRoleService gzRoleService;
    @Autowired
    GzRolePermService gzRolePermService;
    @Autowired
    GzPermissionService gzPermissionService;
    @Autowired
    GzRoleTypeService gzRoleTypeService;

    public void init() {
        LinkedList<GzRoleType> list = new LinkedList<>();
        //初始化角色
        Stream.of(RoleEnum.values()).forEach(item->{
            //初始化角色类型表（固定）
            GzRoleType roleType = new GzRoleType();
            roleType.setRoleId(item.getRoleId());
            roleType.setRoleTypeId(item.getRoleTypeId());
            roleType.setRoleTypeLeavel(item.getLevel());
            roleType.setRoleTypeName(item.getRoleName());
            list.add(roleType);
            GzRole gzRole = createRole(item.getRoleId(), "system", item.getRoleTypeId(), item.getRoleName());
            GzRole role = gzRoleService.getById(item.getRoleId());
            //为空则初始化权限对应表
            if(role==null){
                gzRoleService.save(gzRole);
                List<Integer> permList=gzPermissionService.getIdByRoleType(item.getRoleTypeId());
                try {
                    gzRolePermService.savePerm(item.getRoleId(),permList);
                } catch (SpringSafeException e) {
                    e.printStackTrace();
                }
            }
        });

        gzRoleTypeService.remove(new QWrapper<>());
        gzRoleTypeService.saveBatch(list);
    }


    public GzRole createRole(String roleId,String roleOrgId,Integer roleTypeId,String roleName){
        GzRole role = new GzRole();
        role.setRoleId(roleId);
        role.setRoleTypeId(roleTypeId);
        role.setRoleOrgId(roleOrgId);
        role.setRoleName(roleName);
        role.setRoleStatus(0);
        role.setRoleCreateBy("system");
        role.setRoleUpdateBy("system");
        role.setRoleCreateDate(LocalDateTime.now());
        role.setRoleUpdateDate(LocalDateTime.now());
        return role;

    }
}
