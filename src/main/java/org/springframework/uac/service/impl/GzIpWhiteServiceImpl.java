package org.springframework.uac.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.common.mybatis.QWrapper;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.uac.core.security.SecurityUtils;
import org.springframework.uac.mapper.GzIpWhiteMapper;
import org.springframework.uac.model.domain.GzIpWhite;
import org.springframework.uac.model.dto.config.IpWhiteDto;
import org.springframework.uac.service.GzIpWhiteService;
import org.springframework.uac.utils.IpUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sir.D
 * @since 2020-11-30
 */
@Slf4j
@Service
@Transactional(rollbackFor = SpringSafeException.class)
public class GzIpWhiteServiceImpl extends ServiceImpl<GzIpWhiteMapper, GzIpWhite> implements GzIpWhiteService {


    @Override
    public Integer isAllRelease() {
        QWrapper<GzIpWhite> queryWrapper = new QWrapper();
        queryWrapper.like(GzIpWhite.IP_SEGMENT, "0.0.0.0");
        return this.baseMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<GzIpWhite> findAll(GzIpWhite user) {
        PageHelper.startPage(user.getStart(), user.getLimit());

        QWrapper<GzIpWhite> queryWrapper = new QWrapper();
        queryWrapper
                .orderBy(true, user.isAsc(), user.getProperty() == null ? StringUtils.camelToUnderline(GzIpWhite.ID) : user.camelToUnderline());

        List<GzIpWhite> gzUsers = this.baseMapper.selectList(queryWrapper);
        return new PageInfo<GzIpWhite>(gzUsers);
    }

    @Override
    public void create(IpWhiteDto user) throws SpringSafeException {
        GzIpWhite var0 = new ModelMapper().map(user, GzIpWhite.class);

        var0 = newIpWhite(var0);
        if (!this.save(var0)) {
            throw new SpringSafeException(10001);
        }
    }

    @Override
    public void update(IpWhiteDto user) throws SpringSafeException {
        GzIpWhite var0 = new ModelMapper().map(user, GzIpWhite.class);

        var0 = newIpWhite(var0);
        if (!this.updateById(var0)) {
            throw new SpringSafeException(10001);
        }
    }

    @Override
    public void delete(Integer[] ids) throws SpringSafeException {
        if (!this.removeByIds(Arrays.asList(ids)) ) {
            throw new SpringSafeException(10001);
        }
    }

    private GzIpWhite newIpWhite(GzIpWhite var0) {
        String ipSegment = var0.getIpSegment();
        String[] var1 = ipSegment.split("-");
        String start = var1[0];
        String end = var1[1];
        long var2 = IpUtil.ipToLong(start);
        long var3 = IpUtil.ipToLong(end);

        var0.setIpStart(var2);
        var0.setIpEnd(var3);
        var0.setCreateBy(SecurityUtils.getCurrentLoginName());
        var0.setCreateDate(LocalDateTime.now());
        return var0;
    }
}
