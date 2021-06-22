package org.springframework.uac.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2020-08-04
 */
@Data
@TableName("gz_mail")
@FieldNameConstants(prefix = "")
public class GzMail extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("mail_sender")
    private String mailSender;

    @TableField("mail_recipient")
    private String mailRecipient;

    @TableField("mail_subject")
    private String mailSubject;

    @TableField("mail_content_text")
    private String mailContentText;

    @TableField("mail_status")
    private Integer mailStatus;

    @TableField("create_date")
    private LocalDateTime createDate;

    @TableField("try_number")
    private Integer tryNumber;

    @TableField("success_date")
    private LocalDateTime successDate;

    @TableField("memo")
    private String memo;

}
