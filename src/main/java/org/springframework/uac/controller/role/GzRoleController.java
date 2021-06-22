package org.springframework.uac.controller.role;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.logger.annotation.IRequestMapping;
import org.springframework.logger.annotation.Log;
import org.springframework.logger.utils.LogEventType;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.uac.controller.BaseController;
import org.springframework.uac.model.domain.GzRole;
import org.springframework.uac.model.domain.GzRoleType;
import org.springframework.uac.model.dto.perm.RolePermDto;
import org.springframework.uac.model.dto.role.DeleteRoleDto;
import org.springframework.uac.model.dto.role.RoleDto;
import org.springframework.uac.model.dto.role.RoleListDto;
import org.springframework.uac.model.validate.Create;
import org.springframework.uac.model.validate.Update;
import org.springframework.uac.model.validate.Valid1;
import org.springframework.uac.model.validate.Valid2;
import org.springframework.uac.model.vo.role.RolePermTreeVo;
import org.springframework.uac.service.role.GzRoleService;
import org.springframework.uac.service.role.GzRoleTypeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mayato
 * @since 2020-07-29
 */
@RestController
@IRequestMapping(value = "/gz-role", method = RequestMethod.POST, id = 200, parentIds = 1, roleType = 1, component = "Role")
public class GzRoleController extends BaseController {

    @Autowired
    GzRoleService roleService;

    @Autowired
    GzRoleTypeService roleTypeService;

    /**
     * 角色列表
     *
     * @param role
     * @return
     */
    @Log(eventType = LogEventType.FIND, message = "【角色列表】操作，查询角色列表")
    @IRequestMapping(value = "/list", id = 201)
    public Message list(@Valid @RequestBody RoleListDto role) {
        PageInfo<GzRole> page = roleService.list(role);
        return this.improve(new Message(page));
    }

    /**
     * 创建角色
     *
     * @param role
     * @return
     */
    @Log(eventType = LogEventType.CREATE, message = "【创建角色】操作，创建了【{0}】机构下角色类型为【{1}】的【{2}】角色", params = {"roleOrgId", "roleTypeId", "roleName"})
    @IRequestMapping(value = "/create", id = 202)
    public Message create(@Validated(value = Create.class) @RequestBody RoleDto role) throws SpringSafeException {
        roleService.create(role);
        return this.improve(new Message(Status.SUCCESS));
    }

    /**
     * 更新角色
     *
     * @param role
     * @return
     */
    @Log(eventType = LogEventType.UPDATE, message = "【更新角色】操作，更新了id为【{0}】的角色信息", params = "roleId")
    @IRequestMapping(value = "/update", id = 203)
    public Message update(@Validated(value = Update.class) @RequestBody RoleDto role) throws SpringSafeException {
        roleService.update(role);
        return this.improve(new Message(Status.SUCCESS));
    }

    /**
     * 删除角色
     *
     * @param role
     * @return
     */
    @Log(eventType = LogEventType.DELETE, message = "【删除角色】操作，删除了id为【{0}】的角色信息", params = "ids")
    @IRequestMapping(value = "/delete", id = 204)
    public Message delete(@Validated(value = Valid1.class) @RequestBody DeleteRoleDto role) throws SpringSafeException {
        Message message = roleService.delete(Arrays.asList(role.getIds()));
        if (message == null) {
            return this.improve(new Message(Status.SUCCESS));
        } else {
            return this.improve(message);
        }
    }

    /**
     * 启用角色
     *
     * @param role
     * @return
     */
    @Log(eventType = LogEventType.UPDATE, message = "【启用角色】操作，启用了id为【{0}】的角色信息", params = "roleId")
    @IRequestMapping(value = "/enable", id = 205)
    public Message enableRole(@Validated(value = Valid1.class) @RequestBody RoleDto role) throws SpringSafeException {
        roleService.changeStatus(role, 0);
        return this.improve(new Message(Status.SUCCESS));
    }

    /**
     * 停用角色
     *
     * @param role
     * @return
     */
    @Log(eventType = LogEventType.UPDATE, message = "【停用角色】操作，停用了id为【{0}】的角色信息", params = "roleId")
    @IRequestMapping(value = "/disable", id = 206)
    public Message disableRole(@Validated(value = Valid1.class) @RequestBody RoleDto role) throws SpringSafeException {
        roleService.changeStatus(role, 1);
        return this.improve(new Message(Status.SUCCESS));
    }

    /**
     * 角色权限菜单
     *
     * @param rolePermDto
     * @return
     */
    @IRequestMapping(value = "/perms", id = 207)
    public Message perms(@Validated(Valid1.class) @RequestBody RolePermDto rolePermDto) {
        List<RolePermTreeVo> permTreeVoList = roleService.listPerm(rolePermDto.getRoleId());
        return this.improve(new Message(permTreeVoList));
    }

    /**
     * 授予权限
     *
     * @param rolePermDto
     * @return
     */
    @Log(eventType = LogEventType.UPDATE, message = "【授予权限】操作，授予了了id为【{0}】的角色id为【{1}】的权限", params = {"roleId", "permIds"})
    @IRequestMapping(value = "/assign/perms", id = 208)
    public Message assignRole(@Validated(Valid2.class) @RequestBody RolePermDto rolePermDto) throws SpringSafeException {
        roleService.assignPerms(rolePermDto.getRoleId(), Arrays.asList(rolePermDto.getPermIds()));
        return this.improve(new Message(Status.SUCCESS));
    }


    /**
     * 角色类型列表
     *
     * @return
     */
    @Log(eventType = LogEventType.FIND, message = "【角色类型列表】操作，查询角色类型列表")
    @IRequestMapping(value = "/type/list", id = 209)
    public Message roleTypelist() {
        List<GzRoleType> page = roleTypeService.list();
        return this.improve(new Message(page));
    }

}
