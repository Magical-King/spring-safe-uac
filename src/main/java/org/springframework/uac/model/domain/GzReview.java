package org.springframework.uac.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.uac.model.BaseEntity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Sir.D
 * @since 2020-08-03
 */
@Data
@TableName("gz_review")
@FieldNameConstants(prefix = "")
public class GzReview extends BaseEntity {

    @TableId(value = "review_id", type = IdType.AUTO)
    private Integer reviewId;

    @TableField("review_type_id")
    private Integer reviewTypeId;

    @TableField("review_obj_id")
    private String reviewObjId;

    @TableField("review_auth_id")
    private String reviewAuthId;

    @TableField("review_summary")
    private String reviewSummary;

    @TableField("review_status")
    private Integer reviewStatus;

    @TableField("review_create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reviewCreateDate;

}
