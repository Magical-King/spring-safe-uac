package org.springframework.uac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.uac.mapper.GzVersionMapper;
import org.springframework.uac.model.domain.GzVersion;
import org.springframework.uac.service.GzVersionService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sir.D
 * @since 2020-08-06
 */
@Service
public class GzVersionServiceImpl extends ServiceImpl<GzVersionMapper, GzVersion> implements GzVersionService {

}
