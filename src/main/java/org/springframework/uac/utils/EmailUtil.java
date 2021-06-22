package org.springframework.uac.utils;

import org.springframework.safe.utils.DateUtil;

import java.util.Date;

/**
 * @author Sir.D
 */
public class EmailUtil {

    public static void main(String[] args) {
        String text = text(null, "管理员", "你的验证码为：" + 15648, new Date());
        System.out.println(text);
    }


    public static String addHandleDataIntegrity(String var0) {
        StringBuilder var1 = new StringBuilder();
        var1.append(var0).append("的用户信息为非法添加数据，已依据策略进行了删除！");
        return var1.toString();
    }

    public static String deleteHandleDataIntegrity(String var0) {
        StringBuilder var1 = new StringBuilder();
        var1.append(var0).append("数据被非法删除，已依据策略进行了恢复！");
        return var1.toString();
    }

    public static String modifyHandleDataIntegrity(String var0) {
        StringBuilder var1 = new StringBuilder();
        var1.append(var0).append("数据被非法篡改，已依据策略进行了恢复！");
        return var1.toString();
    }

    /**
     * 用户锁定
     * @param var0 用户id
     * @param var1 用户名
     * @return
     */
    public static String userLock(String var0, String var1, int var2) {
        StringBuilder var3 = new StringBuilder();
        var3.append("用户【").append(var1).append("】由于连续多次(").append(var2).append("次)登录失败，账户【").append(var0).append("】已被锁定！");
        return var3.toString();
    }


    /**
     * 动态密码内容
     * @param var0 验证码
     * @param var1 有效期
     * @return
     */
    public static String dynamicPassword(String var0, int var1) {
        StringBuilder var2 = new StringBuilder();
        var2.append("您本次登陆的验证码为：")
            .append(var0)
            .append(",验证码有效期为")
            .append(var1)
            .append("秒");
        return var2.toString();
    }

    /**
     * 动态密码内容
     * @param var0 操作员->登陆角色
     * @param var1 密码
     * @return
     */
    public static String password(String var0, String var1) {
        StringBuilder var2 = new StringBuilder();
        var2.append("您的密码已被【").append(var0).append("】")
                .append("修改为:").append(var1)
                .append(",请尽快登录并修改密码！");
        return var2.toString();
    }

    /**
     * 组织内容
     * @param var0 发送人
     * @param var1 收件人
     * @param var2 具体内容
     * @param var3 时间
     * @return
     */
    public static String text(String var0, String var1, String var2, Date var3) {
        StringBuilder var5 = new StringBuilder();
        var5.append("尊敬的用户")
            .append("【").append(var1).append("】,您好:\n")
            .append("\n")
            .append("\t").append(var2).append("\n")
            .append("\n")
            .append("\n")
            .append("\t").append("致").append("\n")
            .append("礼！").append("\n")
            .append("\t\t\t\t").append(var0==null ? "国自管理员": var0).append("\n")
            .append(DateUtil.get("yyyy-MM-dd", var3));

        return var5.toString();
    }

}
