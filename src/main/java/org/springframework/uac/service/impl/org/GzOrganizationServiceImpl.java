package org.springframework.uac.service.impl.org;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.common.mybatis.QWrapper;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.uac.model.domain.GzOrganization;
import org.springframework.uac.mapper.org.GzOrganizationMapper;
import org.springframework.uac.model.domain.GzUser;
import org.springframework.uac.service.org.GzOrganizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-30
 */
@Service
@Transactional(rollbackFor = SpringSafeException.class)
public class GzOrganizationServiceImpl extends ServiceImpl<GzOrganizationMapper, GzOrganization> implements GzOrganizationService {

    @Override
    public boolean exists(String orgId) {
        return this.baseMapper.exists(orgId);
    }

    @Override
    public PageInfo<GzOrganization> findAll(GzOrganization org) {
        PageHelper.startPage(org.getStart(), org.getLimit());

        QWrapper<GzOrganization> queryWrapper = new QWrapper();

        queryWrapper.eq(GzOrganization.ORG_ID, org.getOrgId())
                .eq(GzOrganization.ORG_PARENT_ID, org.getOrgParentId())
                .eq(GzOrganization.ORG_TEL, org.getOrgTel())
                .eq(GzOrganization.ORG_FAX, org.getOrgFax())
                .eq(GzOrganization.ORG_POSTCODE, org.getOrgPostcode())
                .eq(GzOrganization.ORG_EMAIL, org.getOrgEmail())
                .eq(GzOrganization.ORG_WEBSITE, org.getOrgWebsite())
                .eq(GzOrganization.ORG_IS_INVALID, org.getOrgIsInvalid())
                .like(GzOrganization.ORG_ADDRESS, org.getOrgAddress())
                .like(GzOrganization.ORG_NAME, org.getOrgName())
                .orderBy(true, org.isAsc(), org.getProperty() == null ? StringUtils.camelToUnderline(GzOrganization.ORG_ID) : org.getProperty());

        List<GzOrganization> gzUsers = this.baseMapper.selectList(queryWrapper);
        return new PageInfo<GzOrganization>(gzUsers);
    }

    @Override
    public void create(GzOrganization org) throws SpringSafeException {
        // 存在则返回报警
        if (this.exists(org.getOrgId())) {
            throw new SpringSafeException(14000);
        }

        if (!this.save(org)) {
            throw new SpringSafeException(10010);
        }
    }

    @Override
    public void update(GzOrganization org) throws SpringSafeException {
        // 存在则返回报警
        if (!this.exists(org.getOrgId())) {
            throw new SpringSafeException(14001);
        }

        if (!this.updateById(org)) {
            throw new SpringSafeException(10011);
        }

    }

    @Override
    public void delete(String[] ids) throws SpringSafeException {
        if (!this.removeByIds(Arrays.asList(ids))) {
            throw new SpringSafeException(10011);
        }
    }
}
