package org.springframework.uac.service.impl.role;

import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.uac.model.domain.GzRoleType;
import org.springframework.uac.mapper.role.GzRoleTypeMapper;
import org.springframework.uac.service.role.GzRoleTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-29
 */
@Service
@Transactional(rollbackFor= SpringSafeException.class)
public class GzRoleTypeServiceImpl extends ServiceImpl<GzRoleTypeMapper, GzRoleType> implements GzRoleTypeService {

}
