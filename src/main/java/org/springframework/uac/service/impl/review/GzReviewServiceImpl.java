package org.springframework.uac.service.impl.review;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.mybatis.QWrapper;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.uac.mapper.review.GzReviewMapper;
import org.springframework.uac.model.domain.GzReview;
import org.springframework.uac.service.perm.GzPermissionService;
import org.springframework.uac.service.review.GzReviewService;
import org.springframework.uac.service.role.GzUserRoleService;
import org.springframework.uac.utils.MessageSourceUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sir.D
 * @since 2020-08-03
 */
@Service
@Transactional(rollbackFor = SpringSafeException.class)
public class GzReviewServiceImpl extends ServiceImpl<GzReviewMapper, GzReview> implements GzReviewService {
    @Autowired
    private MessageSourceUtil source;

    @Autowired
    GzUserRoleService userRoleService;
    @Autowired
    GzPermissionService gzPermissionService;

    @Override
    public PageInfo<GzReview> findAll(GzReview review) {
        PageHelper.startPage(review.getStart(), review.getLimit());

        QWrapper<GzReview> queryWrapper = new QWrapper();
        queryWrapper.eq(GzReview.REVIEW_ID, review.getReviewId())
                .eq(GzReview.REVIEW_TYPE_ID, review.getReviewTypeId())
                .eq(GzReview.REVIEW_STATUS, review.getReviewStatus())
                .like(GzReview.REVIEW_OBJ_ID, review.getReviewObjId())
                .orderBy(true, review.isAsc(), review.getProperty() == null ? StringUtils.camelToUnderline(GzReview.REVIEW_ID) : review.camelToUnderline());

        List<GzReview> gzUsers = this.list(queryWrapper);
        return new PageInfo<GzReview>(gzUsers);
    }

    @Override
    public void create(String objId, String authId, int type, int code) throws SpringSafeException {
        GzReview review = new GzReview();
        review.setReviewObjId(objId);
        review.setReviewSummary(source.message(code));
        review.setReviewTypeId(type);
        review.setReviewAuthId(authId);
        review.setReviewStatus(0);

        if (!this.save(review)) {
            throw new SpringSafeException(10001);
        }
    }

    @Override
    public void approve(Integer reviewId) throws SpringSafeException {
        if (!this.baseMapper.exists(reviewId)) {
            throw new SpringSafeException(20004);
        }

        GzReview review = this.getById(reviewId);
        int a= 1, b =2;
        // 审核用户分配角色
        if (review.getReviewTypeId() == a) {
            this.userRoleService.assignRole(review.getReviewObjId(), review.getReviewAuthId());

            // 审核角色分配请求
        } else if(review.getReviewTypeId() == b) {
            List<Integer> list = Stream.of(review.getReviewAuthId().split(",")).map(Integer::parseInt).collect(Collectors.toList());
            this.gzPermissionService.assignPerm(review.getReviewObjId(),list );
        }

        this.changeStatus(reviewId, 1);
    }

    @Override
    public void refuse(Integer reviewId) throws SpringSafeException {
        if (!this.baseMapper.exists(reviewId)) {
            throw new SpringSafeException(20004);
        }

        this.changeStatus(reviewId, 2);
    }

    @Override
    public void changeStatus(Integer reviewId, Integer status) throws SpringSafeException {
        GzReview review = new GzReview();
        review.setReviewId(reviewId);
        review.setReviewStatus(status);
        if (!this.updateById(review)) {
            throw new SpringSafeException(10001);
        }
    }
}
