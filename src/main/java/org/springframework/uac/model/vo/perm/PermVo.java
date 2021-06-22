package org.springframework.uac.model.vo.perm;

import lombok.Data;

/**
 * @author mayato
 */
@Data
public class PermVo {

    private Integer permId;

    private Integer permType;

    private Integer permParentId;

    private String permParentName;

    private String component;

    private String iframeUrl;

    private String permName;

    private String permUrl;

    private String permRoleType;

    private boolean permIsShow;

}
