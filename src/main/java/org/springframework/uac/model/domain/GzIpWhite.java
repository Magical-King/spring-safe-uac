package org.springframework.uac.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.uac.model.BaseEntity;

import java.time.LocalDateTime;


/**
 * <p>
 *
 * </p>
 *
 * @author Sir.D
 * @since 2020-11-30
 */
@Data
@FieldNameConstants(prefix = "")
@TableName("gz_ip_white")
public class GzIpWhite extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("ip_segment")
    private String ipSegment;

    @TableField("ip_start")
    private Long ipStart;

    @TableField("ip_end")
    private Long ipEnd;

    @TableField("create_by")
    private String createBy;

    @TableField("create_date")
    private LocalDateTime createDate;

}
