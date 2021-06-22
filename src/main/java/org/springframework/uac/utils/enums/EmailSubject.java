package org.springframework.uac.utils.enums;

/**
 * 消息状态
 * @author Sir.D
 *
 */
public enum EmailSubject {
	// 用户动态密码验证
	USER_DYNAMIC_PASSWORD 		( "用户动态密码验证" ),
	// 用户锁定
	USER_LOCK 					( "用户锁定通知" ),
	// 用户重置密码
	USER_RESET_PASSWORD			( "用户重置密码" ),
	// 用户修改密码
	USER_MODIFY_PASSWORD		( "用户修改密码" ),
	// 数据完整性告警
	HANDLE_DATA_INTEGRITY		( "数据完整性告警" ),
	// 系统资源使用率告警
	SYSTEM_RESOURCE_MONITOR_ALARM( "系统资源使用率告警" ),
	;

	/**
	 * code
	 */
	private final String code;
	public String code(){ return code; }
	EmailSubject(String code ) { this.code = code; }
	
	public static EmailSubject fromCode(String code ) {
		for ( EmailSubject source : values() ) {
			if ( source.code.equals(code) ) {
				return source;
			}
		}
		return null;
	}
}
