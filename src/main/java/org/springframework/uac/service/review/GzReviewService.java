package org.springframework.uac.service.review;

import com.github.pagehelper.PageInfo;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.uac.model.domain.GzReview;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Sir.D
 * @since 2020-08-03
 */
public interface GzReviewService extends IService<GzReview> {

    /**
     * 根据条件筛选
     * @param user
     * @return
     * @throws SpringSafeException
     */
    PageInfo<GzReview> findAll(GzReview user);

    /**
     * 审核权限
     * @param objId     对象id->用户的id\角色的id
     * @param authId    权限请求的id->角色的id\按钮请求的id
     * @param type      1：为用户指派角色   2：为角色指派权限
     * @param code      20000：为用户指派角色   20001：为角色指派权限
     * @throws SpringSafeException
     */
    void create(String objId, String authId, int type, int code) throws SpringSafeException;

    /**
     * 审核用户给与角色权限
     * @param reviewId
     * @throws SpringSafeException
     */
    void approve(Integer reviewId) throws SpringSafeException;

    /**
     * 拒绝
     * @param reviewId
     * @throws SpringSafeException
     */
    void refuse(Integer reviewId) throws SpringSafeException;

    /**
     * 修改状态
     * @param reviewId
     * @param status
     * @throws SpringSafeException
     */
    void changeStatus(Integer reviewId, Integer status) throws SpringSafeException;

}
