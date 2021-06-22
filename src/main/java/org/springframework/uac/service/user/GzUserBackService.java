package org.springframework.uac.service.user;

import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.uac.model.domain.GzUser;
import org.springframework.uac.model.domain.GzUserBack;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-28
 */
public interface GzUserBackService extends IService<GzUserBack> {

    /**
     * 添加备份表
     * @param user
     * @return
     */
    boolean save(GzUser user);

    /**
     * 添加备份表
     * @param user
     * @return
     */
    boolean saveOrUpdate(GzUser user);

    /**
     * 添加备份表
     * @param list
     * @return
     */
    boolean saveOrUpdateBatch(List<GzUser> list);

    /**
     * 修改备份表
     * @param user
     * @return
     */
    boolean updateById(GzUser user);

    /**
     * 批量删除
     * @param userIds
     * @return
     */
    boolean deleteByIds(List<String> userIds);
}
