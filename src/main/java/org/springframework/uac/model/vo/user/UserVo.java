package org.springframework.uac.model.vo.user;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.safe.utils.Sm2Util;
import org.springframework.uac.model.vo.org.OrganizationVo;
import org.springframework.uac.model.vo.role.RoleVo;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Sir.D
 */
@Data
public class UserVo {

    public String userId;

    private String userIdEncoded;

    private String userName;

    private Integer userStatus;

    private String userEmail;

    private String userMobile;

    private String userIdentityNumber;

    private Boolean userIsTemporary;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate userEndDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime userUpdateDate;

    private OrganizationVo org;

    private RoleVo role;

    public void setUserId(String userId) {
        this.userId = userId;
        this.userIdEncoded = Sm2Util.encrypt(userId);
    }
}
