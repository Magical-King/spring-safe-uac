package org.springframework.uac.service.org;

import com.github.pagehelper.PageInfo;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.uac.model.domain.GzOrganization;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.uac.model.dto.user.UserDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-30
 */
public interface GzOrganizationService extends IService<GzOrganization> {

    /**
     * 判断是否存在
     * @param orgId
     * @return
     */
    boolean exists( String orgId);

    /**
     * 根据条件筛选
     * @param org
     * @return
     * @throws SpringSafeException
     */
    PageInfo<GzOrganization> findAll(GzOrganization org);

    /**
     * 创建组织
     * @param org
     * @throws SpringSafeException
     */
    void create(GzOrganization org) throws SpringSafeException;

    /**
     * 编辑组织
     * @param org
     * @throws SpringSafeException
     */
    void update(GzOrganization org) throws SpringSafeException;

    /**
     * 删除组织
     * @param ids
     * @throws SpringSafeException
     */
    void delete(String[] ids) throws SpringSafeException;

}
