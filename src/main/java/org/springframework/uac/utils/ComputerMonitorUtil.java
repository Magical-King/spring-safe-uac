package org.springframework.uac.utils;

import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Sir.D
 */
@Slf4j
public class ComputerMonitorUtil {
    private static String osName = System.getProperty("os.name");
    private static final int CPUTIME = 500;
    private static final int PERCENT = 100;
    private static final int FAULTLENGTH = 10;


    public static double getCpuUsage() {
        if (!osName.toLowerCase().contains("windows") && !osName.toLowerCase().contains("win")) {
            try {
                Map var34 = cpuinfo();
                Thread.sleep(500L);
                Map var35 = cpuinfo();
                long var36 = Long.parseLong(var34.get("user").toString());
                long var4 = Long.parseLong(var34.get("nice").toString());
                long var6 = Long.parseLong(var34.get("system").toString());
                long var37 = Long.parseLong(var34.get("idle").toString());
                long var10 = Long.parseLong(var35.get("user").toString());
                long var12 = Long.parseLong(var35.get("nice").toString());
                long var14 = Long.parseLong(var35.get("system").toString());
                long var16 = Long.parseLong(var35.get("idle").toString());
                long var18 = var36 + var6 + var4;
                long var20 = var10 + var14 + var12;
                float var22 = (float)(var20 - var18);
                long var23 = var36 + var4 + var6 + var37;
                long var25 = var10 + var12 + var14 + var16;
                float var27 = (float)(var25 - var23);
                float var28 = var22 / var27 * 100.0F;
                BigDecimal var29 = new BigDecimal((double)var28);
                double var30 = var29.setScale(2, 4).doubleValue();
                return var30;
            } catch (InterruptedException var32) {
                log.debug("===> {}",var32);
                return 0.0D;
            }
        } else {
            try {
                String var0 = System.getenv("windir") + "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
                long[] var1 = a(Runtime.getRuntime().exec(var0));
                Thread.sleep(500L);
                long[] var2 = a(Runtime.getRuntime().exec(var0));
                if (var1 != null && var2 != null) {
                    long var3 = var2[0] - var1[0];
                    long var5 = var2[1] - var1[1];
                    Double var7 = (double)(100L * var5) * 1.0D / (double)(var5 + var3);
                    BigDecimal var8 = new BigDecimal(var7);
                    double var9 = var8.setScale(2, 4).doubleValue();
                    return var9;
                } else {
                    return 0.0D;
                }
            } catch (Exception var33) {
                log.debug("===> {}",var33);
                return 0.0D;
            }
        }
    }

    public static Map<?, ?> cpuinfo() {
        InputStreamReader var0 = null;
        BufferedReader var1 = null;
        HashMap var2 = new HashMap();

        try {
            var0 = new InputStreamReader(new FileInputStream("/proc/stat"));
            var1 = new BufferedReader(var0);
            String var3 = "";

            while(true) {
                var3 = var1.readLine();
                if (var3 == null) {
                    break;
                }

                if (var3.startsWith("cpu")) {
                    StringTokenizer var4 = new StringTokenizer(var3);
                    ArrayList var5 = new ArrayList();

                    while(var4.hasMoreElements()) {
                        String var6 = var4.nextToken();
                        var5.add(var6);
                    }

                    var2.put("user", var5.get(1));
                    var2.put("nice", var5.get(2));
                    var2.put("system", var5.get(3));
                    var2.put("idle", var5.get(4));
                    var2.put("iowait", var5.get(5));
                    var2.put("irq", var5.get(6));
                    var2.put("softirq", var5.get(7));
                    var2.put("stealstolen", var5.get(8));
                    break;
                }
            }
        } catch (Exception var15) {
            log.debug("===> {}",var15);
        } finally {
            try {
                var1.close();
                var0.close();
            } catch (Exception var14) {
                log.debug("===> {}",var14);
            }

        }

        return var2;
    }

    public static double getMemUsage() {
        if (!osName.toLowerCase().contains("windows") && !osName.toLowerCase().contains("win")) {
            HashMap var33 = new HashMap();
            InputStreamReader var34 = null;
            BufferedReader var2 = null;

            try {
                var34 = new InputStreamReader(new FileInputStream("/proc/meminfo"));
                var2 = new BufferedReader(var34);
                String var35 = "";

                while(true) {
                    var35 = var2.readLine();
                    if (var35 == null) {
                        long var37 = Long.parseLong(var33.get("MemTotal").toString());
                        long var40 = Long.parseLong(var33.get("MemFree").toString());
                        long var42 = var37 - var40;
                        long var10 = Long.parseLong(var33.get("Buffers").toString());
                        long var12 = Long.parseLong(var33.get("Cached").toString());
                        double var14 = (double)(var42 - var10 - var12) / (double)var37 * 100.0D;
                        BigDecimal var16 = new BigDecimal(var14);
                        double var17 = var16.setScale(2, 4).doubleValue();
                        double var20 = var17;
                        return var20;
                    }

                    byte var4 = 0;
                    int var38 = var35.indexOf(":");
                    if (var38 != -1) {
                        String var39 = var35.substring(var4, var38);
                        int var36 = var38 + 1;
                        var38 = var35.length();
                        String var41 = var35.substring(var36, var38);
                        String var8 = var41.replace("kB", "").trim();
                        var33.put(var39, var8);
                    }
                }
            } catch (Exception var30) {
                log.debug("===> {}", var30);
                return 0.0D;
            } finally {
                try {
                    var2.close();
                    var34.close();
                } catch (Exception var29) {
                    log.debug("===> {}", var29);
                }

            }
        } else {
            try {
                OperatingSystemMXBean var0 = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                long var1 = var0.getTotalSwapSpaceSize();
                long var3 = var0.getFreePhysicalMemorySize();
                Double var5 = Double.valueOf(1.0D - (double)var3 * 1.0D / (double)var1) * 100.0D;
                BigDecimal var6 = new BigDecimal(var5);
                double var7 = var6.setScale(2, 4).doubleValue();
                return var7;
            } catch (Exception var32) {
                log.debug("===> {}", var32);
                return 0.0D;
            }
        }
    }

    public static double getDiskUsage() throws Exception {
        double var0 = 0.0D;
        double var2 = 0.0D;
        Double var22;
        BigDecimal var23;
        if (!osName.toLowerCase().contains("windows") && !osName.toLowerCase().contains("win")) {
            Runtime var19 = Runtime.getRuntime();
            Process var5 = var19.exec("df -hl");
            BufferedReader var20 = null;

            try {
                var20 = new BufferedReader(new InputStreamReader(var5.getInputStream()));
                String var7 = null;
                var22 = null;

                while((var7 = var20.readLine()) != null) {
                    int var24 = 0;
                    String[] var25 = var7.split(" ");
                    String[] var28 = var25;
                    int var12 = var25.length;

                    for(int var27 = 0; var27 < var12; ++var27) {
                        String var26 = var28[var27];
                        if (var26.trim().length() != 0) {
                            ++var24;
                            if (var26.indexOf("G") != -1) {
                                if (var24 == 2 && !var26.equals("") && !var26.equals("0")) {
                                    var0 += Double.parseDouble(var26.substring(0, var26.length() - 1)) * 1024.0D;
                                }

                                if (var24 == 3 && !var26.equals("none") && !var26.equals("0")) {
                                    var2 += Double.parseDouble(var26.substring(0, var26.length() - 1)) * 1024.0D;
                                }
                            }

                            if (var26.indexOf("M") != -1) {
                                if (var24 == 2 && !var26.equals("") && !var26.equals("0")) {
                                    var0 += Double.parseDouble(var26.substring(0, var26.length() - 1));
                                }

                                if (var24 == 3 && !var26.equals("none") && !var26.equals("0")) {
                                    var2 += Double.parseDouble(var26.substring(0, var26.length() - 1));
                                }
                            }
                        }
                    }
                }
            } catch (Exception var17) {
                log.debug("===> {}",var17);
            } finally {
                var20.close();
            }

            double var21 = var2 / var0 * 100.0D;
            var23 = new BigDecimal(var21);
            var21 = var23.setScale(2, 4).doubleValue();
            return var21;
        } else {
            long var4 = 0L;
            long var6 = 0L;

            for(char var8 = 'A'; var8 <= 'Z'; ++var8) {
                String var9 = var8 + ":/";
                File var10 = new File(var9);
                if (var10.exists()) {
                    long var11 = var10.getTotalSpace();
                    long var13 = var10.getFreeSpace();
                    var4 += var11;
                    var6 += var13;
                }
            }

            var22 = Double.valueOf(1.0D - (double)var6 * 1.0D / (double)var4) * 100.0D;
            var23 = new BigDecimal(var22);
            var22 = var23.setScale(2, 4).doubleValue();
            return var22;
        }
    }

    private static long[] a(Process var0) {
        long[] var1 = new long[2];

        long[] var24;
        try {
            var0.getOutputStream().close();
            InputStreamReader var2 = new InputStreamReader(var0.getInputStream());
            LineNumberReader var3 = new LineNumberReader(var2);
            String var4 = var3.readLine();
            if (var4 == null || var4.length() < 10) {
                return null;
            }

            int var5 = var4.indexOf("Caption");
            int var6 = var4.indexOf("CommandLine");
            int var7 = var4.indexOf("ReadOperationCount");
            int var8 = var4.indexOf("UserModeTime");
            int var9 = var4.indexOf("KernelModeTime");
            int var10 = var4.indexOf("WriteOperationCount");
            long var11 = 0L;
            long var13 = 0L;
            long var15 = 0L;

            while((var4 = var3.readLine()) != null) {
                if (var4.length() >= var10) {
                    String var17 = substring(var4, var5, var6 - 1).trim();
                    String var18 = substring(var4, var6, var9 - 1).trim();
                    if (var18.indexOf("wmic.exe") < 0) {
                        String var19 = substring(var4, var9, var7 - 1).trim();
                        String var20 = substring(var4, var8, var10 - 1).trim();
                        List var21 = q(var19);
                        List var22 = q(var20);
                        if (!var17.equals("System Idle Process") && !var17.equals("System")) {
                            if (var19.length() > 0 && !((String)var21.get(0)).equals("") && var21.get(0) != null) {
                                var13 += Long.valueOf((String)var21.get(0));
                            }

                            if (var20.length() > 0 && !((String)var22.get(0)).equals("") && var22.get(0) != null) {
                                var13 += Long.valueOf((String)var22.get(0));
                            }
                        } else {
                            if (var19.length() > 0 && !((String)var21.get(0)).equals("") && var21.get(0) != null) {
                                var11 += Long.valueOf((String)var21.get(0));
                            }

                            if (var20.length() > 0 && !((String)var22.get(0)).equals("") && var22.get(0) != null) {
                                var11 += Long.valueOf((String)var22.get(0));
                            }
                        }
                    }
                }
            }

            var1[0] = var11;
            var1[1] = var13 + var15;
            var24 = var1;
        } catch (Exception var33) {
            log.debug("===> {}",var33);
            return null;
        } finally {
            try {
                var0.getInputStream().close();
            } catch (Exception var32) {
                log.debug("===> {}",var32);
            }

        }

        return var24;
    }

    private static List<String> q(String var0) {
        ArrayList var1 = new ArrayList();
        var1.add(var0.replaceAll("\\D", ""));
        return var1;
    }

    private static String substring(String var0, int var1, int var2) {
        byte[] var3 = var0.getBytes();
        String var4 = "";

        for(int var5 = var1; var5 <= var2; ++var5) {
            var4 = var4 + (char)var3[var5];
        }

        return var4;
    }

    public static void main(String[] var0) throws Exception {
        double var1 = getCpuUsage();
        double var3 = getMemUsage();
        double var5 = getDiskUsage();
        System.out.println("cpuUsage:" + var1);
        System.out.println("memUsage:" + var3);
        System.out.println("diskUsage:" + var5);
    }

}
