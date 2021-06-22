package org.springframework.uac.service.impl.user;

import org.modelmapper.ModelMapper;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.uac.model.domain.GzUser;
import org.springframework.uac.model.domain.GzUserBack;
import org.springframework.uac.mapper.user.GzUserBackMapper;
import org.springframework.uac.service.user.GzUserBackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-28
 */
@Service
@Transactional(rollbackFor= SpringSafeException.class)
public class GzUserBackServiceImpl extends ServiceImpl<GzUserBackMapper, GzUserBack> implements GzUserBackService {

    @Override
    public boolean save(GzUser user) {
        GzUserBack userBack = new ModelMapper().map(user, GzUserBack.class);
        return this.save(userBack);
    }

    @Override
    public boolean saveOrUpdate(GzUser user) {
        GzUserBack userBack = new ModelMapper().map(user, GzUserBack.class);
        return this.saveOrUpdate(userBack);
    }

    @Override
    public boolean saveOrUpdateBatch(List<GzUser> list) {
        List<GzUserBack> backList = list.stream().map(e -> {
            return new ModelMapper().map(e, GzUserBack.class);
        }).collect(Collectors.toList());

        return this.saveOrUpdateBatch(backList);
    }

    @Override
    public boolean updateById(GzUser user) {
        GzUserBack userBack = new ModelMapper().map(user, GzUserBack.class);
        return this.updateById(userBack);
    }

    @Override
    public boolean deleteByIds(List<String> userIds) {
        return this.removeByIds(userIds);
    }
}
