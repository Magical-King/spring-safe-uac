package org.springframework.uac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.uac.model.domain.GzIpWhite;
import org.springframework.uac.model.dto.config.IpWhiteDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Sir.D
 * @since 2020-11-30
 */
public interface GzIpWhiteService extends IService<GzIpWhite> {


    Integer isAllRelease();

    PageInfo<GzIpWhite> findAll(GzIpWhite user);

    /**
     * 创建用户
     * @param ipWhite
     * @throws SpringSafeException
     */
    void create(IpWhiteDto ipWhite) throws SpringSafeException;

    /**
     * 编辑用户
     * @param ipWhite
     * @throws SpringSafeException
     */
    void update(IpWhiteDto ipWhite) throws SpringSafeException;


    /**
     * 删除用户
     * @param ids
     * @throws SpringSafeException
     */
    void delete(Integer[] ids) throws SpringSafeException;

}
