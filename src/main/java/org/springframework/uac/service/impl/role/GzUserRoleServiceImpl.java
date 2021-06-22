package org.springframework.uac.service.impl.role;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.common.mybatis.QWrapper;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.uac.mapper.role.GzUserRoleMapper;
import org.springframework.uac.model.domain.GzUserRole;
import org.springframework.uac.service.role.GzUserRoleService;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-29
 */
@Service
@Transactional(rollbackFor=SpringSafeException.class)
public class GzUserRoleServiceImpl extends ServiceImpl<GzUserRoleMapper, GzUserRole> implements GzUserRoleService {

    @Override
    public void assignRole(String userId, String roleId) throws SpringSafeException {
        QWrapper<GzUserRole> qWrapper = new QWrapper<>();
        qWrapper.eq(GzUserRole.USER_ID, userId);
        if (StringUtils.isNotBlank(roleId)) {
            GzUserRole role = new GzUserRole(userId+roleId, roleId, userId);
            if(!this.saveOrUpdate(role,qWrapper)) {
                throw new SpringSafeException(12001);
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public String findRoleIdByUserId(String userId) {
        QWrapper<GzUserRole> qWrapper = new QWrapper<>();
        qWrapper.eq(GzUserRole.USER_ID, userId);

        GzUserRole temp = this.getOne(qWrapper);
        if (null == temp) {
            return null;
        }

        return temp.getRoleId();
    }

    @Override
    public List<String> findRoleIdListInRoleIds(List<String> ids) {

        QWrapper<GzUserRole> qWrapper = new QWrapper<GzUserRole>();
        qWrapper.in(GzUserRole.ROLE_ID, ids)
                .select("distinct "+ com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(GzUserRole.ROLE_ID));
        List<String> list = this.listObjs(qWrapper, String::valueOf);
        return list;
    }

    @Override
    public void delete() {
        this.baseMapper.delete();
    }

}
