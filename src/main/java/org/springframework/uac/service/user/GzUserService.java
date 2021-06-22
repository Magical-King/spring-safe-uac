package org.springframework.uac.service.user;

import com.github.pagehelper.PageInfo;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.uac.model.domain.GzUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.uac.model.dto.user.ModifyPwdDto;
import org.springframework.uac.model.dto.user.UserDto;
import org.springframework.uac.model.vo.user.UserVo;
import org.springframework.uac.core.security.PcGrantedAuthority;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-28
 */
public interface GzUserService extends IService<GzUser> {

    /**
     * 查找初始化角色
     * @return
     */
    List<String> findInitUser();

    /**
     * 根据条件筛选
     * @param user
     * @return
     * @throws SpringSafeException
     */
    PageInfo<UserVo> findAll(GzUser user);

    /**
     * 创建用户
     * @param user
     * @throws SpringSafeException
     */
    void create(UserDto user) throws SpringSafeException;

    /**
     * 编辑用户
     * @param user
     * @throws SpringSafeException
     */
    void update(UserDto user) throws SpringSafeException;

    /**
     * 修改
     * @param userId
     */
    void update(String userId);


    /**
     * 重置密码
     * @param userIdEncodeds
     * @throws SpringSafeException
     */
    void restPwd(String[] userIdEncodeds) throws SpringSafeException;

    /**
     * 修改密码
     * @param pwd
     * @throws SpringSafeException
     */
    void modifyPwd(ModifyPwdDto pwd) throws SpringSafeException;

    /**
     * 批量修改状态
     * @param userIdEncodeds 用户ids
     * @param status 状态
     * @throws SpringSafeException
     */
    void batchChangeStatus(String[] userIdEncodeds, int status) throws SpringSafeException;

    /**
     * 修改状态
     * @param userIdEncoded 用户ids
     * @param status 状态
     * @throws SpringSafeException
     */
    void changeStatus(String userIdEncoded, int status) throws SpringSafeException;

    /**
     * 删除用户
     * @param userIdEncodeds
     * @throws SpringSafeException
     */
    void delete(String[] userIdEncodeds) throws SpringSafeException;

    /**
     * 给用户分配角色
     * @param user
     * @throws SpringSafeException
     */
    void assignRole(UserDto user) throws SpringSafeException;

    /**
     * 用户id
     * @param userId
     * @return
     * @throws SpringSafeException
     */
    boolean updateUserLoginFailStatus(String userId) throws SpringSafeException;


    /**
     * 获取用户权限
     * @param userId
     * @return
     */
    List<PcGrantedAuthority> findAuthority(String userId);


    /**
     * 验证数据完整性
     * @param userId
     * @return
     */
    boolean checkDataIntegrity(String userId);

    /**
     * 确认密码
     * @param password
     * @throws SpringSafeException
     */
    void checkPassword(String password) throws SpringSafeException;
}
