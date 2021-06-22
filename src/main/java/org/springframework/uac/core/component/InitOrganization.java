package org.springframework.uac.core.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.uac.model.domain.GzOrganization;
import org.springframework.uac.service.org.GzOrganizationService;

/**
 * @author Sir.D
 */
@Configuration
public class InitOrganization {

    @Autowired
    private GzOrganizationService service;

    public void init() {
        GzOrganization org = this.service.getById("system");
        if (null == org) {
            org = new GzOrganization();
            org.setOrgId("system");
            org.setOrgCode("gzrobot");
            org.setOrgName("浙江国自机器人技术股份有限公司");
            org.setOrgAddress("浙江省杭州市滨江区东信大道66号4幢5层501-516、518室");
            org.setOrgWebsite("http://www.gzrobot.com/");
            org.setOrgDescription("浙江国自机器人技术股份有限公司成立于2011年11月10日，注册地位于浙江省杭州市滨江区东信大道66号4幢5层501-516、518室，法定代表人为郑洪波。经营范围包括智能服务机器人、智能系统、电气自动化设备、计算机、电子计算机设备、电子设备、应用软件的研发、生产、安装及技术服务，智能工程、自动化工程的集成、技术咨询、工程建设及安装，计算机、电子设备的销售，经营进出口业务。（依法须经批准的项目，经相关部门批准后方可开展经营活动）浙江国自机器人技术股份有限公司对外投资5家公司。");
            org.setOrgIsInvalid(true);
            this.service.save(org);
        }
    }

}
