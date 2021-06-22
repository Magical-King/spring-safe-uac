package org.springframework.uac.core.security.handleipwhite;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.security.validate.code.exception.ValidateCodeException;
import org.springframework.safe.security.validate.ipwhite.HandleIpWhite;
import org.springframework.stereotype.Component;
import org.springframework.uac.model.domain.GzIpWhite;
import org.springframework.uac.service.GzIpWhiteService;
import org.springframework.uac.utils.IpUtil;
import org.springframework.uac.utils.MessageSourceUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Sir.D
 */
@Slf4j
@Component
public class PcHandleIpWhite implements HandleIpWhite {

    public static CopyOnWriteArrayList<GzIpWhite> list = new CopyOnWriteArrayList<>();

    public static String REMOTE_ADDR = "X-Real-IP";

    public volatile static Boolean isAllRelease = false;


    @Autowired
    private GzIpWhiteService ipWhiteService;

    @Autowired
    private MessageSourceUtil sourceUtil;


    @Override
    public void validate(HttpServletRequest httpServletRequest) throws SpringSafeException {
        String remoteAddr = httpServletRequest.getHeader(REMOTE_ADDR);
        if (StringUtils.isBlank(remoteAddr)) {
            remoteAddr = httpServletRequest.getRemoteAddr();
        }

        if (null == list || list.isEmpty()) {
            isAllRelease = SqlHelper.retBool(ipWhiteService.isAllRelease());
            if (isAllRelease) {
                list.add(new GzIpWhite());
            } else {
                List<GzIpWhite> ipWhites = ipWhiteService.list();
                if (null != ipWhites) {
                    list.addAll(ipWhites);
                }
            }
        }

        Long ipToLong = IpUtil.ipToLong(remoteAddr);
        boolean flag = false;
        if (!isAllRelease) {
            for (GzIpWhite ipWhite : list) {
                Long start = ipWhite.getIpStart();
                Long end = ipWhite.getIpEnd();

                if ( start <= ipToLong && end >= ipToLong) {
                    flag = true;
                    break;
                }
            }
        }

        if (!flag && !isAllRelease) {
            throw new ValidateCodeException(sourceUtil.message("50000"));
        }
    }

    public static void clear() {
        isAllRelease = false;
        list.clear();
    }
}
