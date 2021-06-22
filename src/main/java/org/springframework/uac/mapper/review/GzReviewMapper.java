package org.springframework.uac.mapper.review;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.uac.model.domain.GzReview;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Sir.D
 * @since 2020-08-03
 */
public interface GzReviewMapper extends BaseMapper<GzReview> {

    /**
     * 查询审核记录是否存在
     * @param reviewId
     * @return
     */
    @Select("SELECT CASE WHEN COUNT(1) > 0 THEN true ELSE false END FROM gz_review t WHERE t.review_id = #{reviewId} and review_status = 0")
    boolean exists(@Param("reviewId") int reviewId);

}
