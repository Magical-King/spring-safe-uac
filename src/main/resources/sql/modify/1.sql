INSERT INTO `gz_security_config` VALUES ('SYSTEM_CREATE_RANDOM_PASSWORD_FLAG', '随机数密码标识', 0, '8', 0, 'system', '2020-07-25 11:02:38', 'system', '2020-07-25 11:02:44', '是否开启随机数密码(默认开启,关闭则为固定密码模式)，可定义随机数密码长度');
INSERT INTO `gz_security_config` VALUES ('SYSTEM_LOGIN_FAIL_CHECKPERIOD_HOURS', '用户登录失败间隔时间（单位：小时）', 1, '1', 0, 'system', '2020-07-25 11:02:38', 'system', '2020-07-25 11:02:38', '');
INSERT INTO `gz_security_config` VALUES ('SYSTEM_LOGIN_FAIL_LOCK_MINS', '用户登录失败锁定时间（单位：分）', 1, '20', 0, 'system', '2020-07-25 11:02:38', 'system', '2020-07-25 11:02:38', NULL);
INSERT INTO `gz_security_config` VALUES ('SYSTEM_LOGIN_FAIL_MAX_NUM', '用户登录失败最大次数', 1, '5', 0, 'system', '2020-07-25 11:02:38', 'system', '2020-07-25 11:02:38', NULL);
INSERT INTO `gz_security_config` VALUES ('SYSTEM_LOGIN_PASSWORD_TYPE', '系统登录强密码模式', 1, '(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[~!@#$%^&*.?,\\-=_/+])[a-zA-Z0-9~!@#$%^&*.?,\\-=_/+]{8,}', 1, 'system', '2020-07-25 11:02:38', 'system', '2020-07-25 11:02:38', '密码长度不允许小于8位，并且其中必须包含字母、数组、特殊字符');
INSERT INTO `gz_security_config` VALUES ('SYSTEM_LOGIN_PASSWORD_VALID_DAYS', '用户登录密码更新周期（单位：天）', 1, '30', 0, 'system', '2020-07-25 11:02:38', 'system', '2020-07-25 11:02:38', NULL);
INSERT INTO `gz_security_config` VALUES ('SYSTEM_LOG_STORAGE_ALARM_PERCENT', '审计日志存储空间利用率告警门限', 1, '78', 1, 'system', '2020-05-30 01:33:01', 'system', '2020-05-30 01:40:43', '');
INSERT INTO `gz_security_config` VALUES ('SYSTEM_LOG_STORAGE_LIMIT', '审计日志存储空间限额', 1, '10', 1, 'system', '2020-05-30 01:22:02', 'system', '2020-05-30 01:22:09', '单位gb');
INSERT INTO `gz_security_config` VALUES ('SYSTEM_PASSWORD_DIGEST_ALGORITHM', '系统密码存储摘要算法', 1, 'SM3', 1, 'system', '2020-07-25 11:02:38', 'system', '2020-07-25 11:02:38', 'SM3或md5');
INSERT INTO `gz_security_config` VALUES ('SYSTEM_REVIEW_FLAG', '是否开启审核员功能', 1, 'true', 1, 'system', '2020-07-25 11:02:38', 'system', '2020-07-25 11:02:38', NULL);
INSERT INTO `gz_security_config` VALUES ('SYSTEM_SEND_EMAIL_ON_FLAG', '是否开启发送邮箱功能', 1, 'false', 1, 'system', '2020-07-25 11:02:38', 'system', '2020-07-25 11:02:38', NULL);

INSERT INTO `gz_ip_white` VALUES (1, '0.0.0.0-0.0.0.0', 0, 0, 'system', '2020-11-30 19:03:18');