package org.springframework.uac.model.vo.perm;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayato
 */
@Data
public class MenuVo {

    Integer permId;

    String permName;

    Integer permType;

    Integer permParentId;

    String component;

    String iframeUrl;

    String permUrl;

    String permUrlPrefix;

    List<MenuVo> menuVoList=new ArrayList<>();
}
