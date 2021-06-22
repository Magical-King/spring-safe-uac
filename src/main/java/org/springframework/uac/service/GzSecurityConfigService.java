package org.springframework.uac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.uac.model.domain.GzSecurityConfig;
import org.springframework.uac.model.vo.config.SecurityConfigVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Sir.D
 * @since 2020-07-28
 */
public interface GzSecurityConfigService extends IService<GzSecurityConfig> {

    /**
     * 判断是否存在库
     * @param tableSchema
     * @return
     */
    boolean exists(String tableSchema);

    /**
     * 根据id找配置
     * @param var1
     * @param var2
     * @return
     */
    String findConfigStrById(String var1, String var2);


    /**
     * 系统资源占用告警门限
     * @return
     */
    int resourceLimitPercent();

    /**
     * 用户登录失败间隔时间（单位：小时）
     * 默认1
     * @return
     */
    String loginFailCheckperiodHours();

    /**
     * 用户登录失败锁定时间（单位：分）
     * 默认20
     * @return
     */
    String loginFailLockMins();

    /**
     * 用户登录失败最大次数
     * 默认5
     * @return
     */
    String loginFailMaxNum();

    /**
     * 获取用户登录密码更新周期（单位：天）
     * 默认30天后
     * @param time
     * @return
     */
    LocalDateTime loginPsswordValidDays(LocalDateTime time);

    /**
     * 获取用户登录密码更新周期（单位：天）
     * 默认30天后
     * @return
     */
    String loginPasswordValidDays();

    /**
     * 是否开启审核员功能
     * 默认false
     * @return
     */
    boolean reviewFlag();

    /**
     * 是否开启发送邮箱
     * 默认false
     * @return
     */
    boolean sendEmailFlag();

    /**
     * 密码策略获取加密密码
     * @param var1 用户名
     * @param var2 用户邮箱
     * @return
     * @throws SpringSafeException
     */
    String password(String var1, String var2) throws SpringSafeException;

    /**
     * 密码策略修改加密密码
     * @param var1 用户名
     * @param var2 用户邮箱
     * @param var3 数据库原始密码
     * @param var4 用户输入原始密码
     * @param var5 修改密码
     * @return
     * @throws SpringSafeException
     */
    String password(String var1, String var2, String var3, String var4, String var5) throws SpringSafeException;

    /**
     * 查询非系统配置列表
     * @return
     */
    List<SecurityConfigVo> listNonSys();

    /**
     * 更新非系统配置
     * @param configId
     * @param configValue
     * @param remake
     * @throws SpringSafeException
     */
    void updateNonSys(String configId, String configValue, String remake) throws SpringSafeException;
}
