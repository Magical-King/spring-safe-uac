package org.springframework.uac.service.user;

import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.uac.model.domain.GzUserModified;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Sir.D
 * @since 2020-08-07
 */
public interface GzUserModifiedService extends IService<GzUserModified> {

    /**
     * 数据完整性
     * @throws SpringSafeException
     */
    void handleDataIntegrity() throws SpringSafeException;

}
