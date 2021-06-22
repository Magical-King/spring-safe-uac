package org.springframework.uac.model.vo.role;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayato
 */
@Data
public class RolePermTreeVo {

    Integer permId;

    String permName;

    Integer permType;

    Integer permParentId;

    List<RolePermTreeVo> permTreeVoList=new ArrayList<>();

}
