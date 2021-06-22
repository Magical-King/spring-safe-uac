package org.springframework.uac.service.impl.perm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.common.mybatis.QWrapper;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.uac.model.domain.GzRolePerm;
import org.springframework.uac.mapper.perm.GzRolePermMapper;
import org.springframework.uac.model.domain.GzUserRole;
import org.springframework.uac.service.perm.GzRolePermService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mayato
 * @since 2020-07-29
 */
@Service
@Transactional(rollbackFor = SpringSafeException.class)
public class GzRolePermServiceImpl extends ServiceImpl<GzRolePermMapper, GzRolePerm> implements GzRolePermService {

    @Transactional(readOnly = true)
    @Override
    public List<String> findRoleIdListInRoleIds(List<String> ids) {
        QWrapper<GzRolePerm> queryWrapper2 = new QWrapper<GzRolePerm>();
        queryWrapper2.in(GzRolePerm.ROLE_ID, ids)
                .select("distinct " + StringUtils.camelToUnderline(GzUserRole.ROLE_ID));
        List<String> list = this.listObjs(queryWrapper2, String::valueOf);
        return list;
    }

    @Override
    public void savePerm(String roleId, List<Integer> permList) throws SpringSafeException {
        //批量删除后保存
        if (permList.isEmpty()) {
            return;
        }
        List<GzRolePerm> rolePermList = permList.stream().map(item -> {
            GzRolePerm rolePerm = new GzRolePerm();
            rolePerm.setPermId(item);
            rolePerm.setRoleId(roleId);
            return rolePerm;
        }).collect(Collectors.toList());
        QueryWrapper<GzRolePerm> wrapper = new QWrapper<GzRolePerm>()
                .eq(GzRolePerm.ROLE_ID, roleId)
                .in(GzRolePerm.PERM_ID, permList);
        //不抛出异常因为可能为空
        this.remove(wrapper);
        if (!this.saveBatch(rolePermList)) {
            throw new SpringSafeException(10008);
        }
    }

    @Override
    public void removeByRoleId(String roleId) {
        //防止全删
        if (roleId != null) {
            this.remove(new QWrapper<GzRolePerm>()
                    .eq(GzRolePerm.ROLE_ID, roleId));
        }
    }
    @Transactional(readOnly = true)
    @Override
    public List<GzRolePerm> findByRoleAndPermIds(String roleId, List<Integer> permIds) {
        QueryWrapper<GzRolePerm> wrapper = new QWrapper<GzRolePerm>().eq(GzRolePerm.ROLE_ID, roleId)
                .in(GzRolePerm.PERM_ID, permIds);

        return this.list(wrapper);
    }
}
