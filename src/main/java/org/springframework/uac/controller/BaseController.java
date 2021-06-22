package org.springframework.uac.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.stereotype.Controller;
import org.springframework.uac.utils.MessageSourceUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Sir.D
 */
@Slf4j
@Controller
public class BaseController {
	protected final Lock syncLock = new ReentrantLock();

	/**
	 * 所有的消息源都来自资源文件
	 */
	@Autowired
	protected MessageSourceUtil source;
	
	/**
	 * 直接注入HttpServletRequest
	 */
	@Autowired
	protected HttpServletRequest request;
	
	/**
	 * 获取 HttpSession
	 * @return
	 */
	protected HttpSession getSession(){
		return request.getSession();
	}

	/**
	 * 获取平台的URL地址
	 * @return
	 */
	protected String getHttpUrl() {
		return new StringBuilder()
			.append( request.getScheme() ).append( "://" ) 
			.append( request.getServerName() ).append( ":" )
			.append( request.getServerPort() )
			.append( request.getContextPath() )
			.toString();
	}

	/**
	 * Voice/Excel 临时WEB地址
	 * @param fileName
	 * @return
	 */
//	protected String getTempUrl( String fileName ){
//		return new StringBuilder()
//			.append( this.getHttpUrl() )
//			.append( "/resource-" ).append( app.getVersion() )
//			.append( "/tmp/" ).append( fileName )
//			.toString();
//	}

	/**
	 * 获得文件在容器下的物理路径
	 * @param arg0	路径名称
	 * @return
	 */
	protected String getPhysicsPath( String arg0 ) {
		return this.getSession().getServletContext().getRealPath( arg0 );
	}

	/**
	 * Voice/Excel 临时存放的物理路径
	 * @return
	 */
//	protected String getTempPath(){
//		String tempPath = this.getPhysicsPath( "META-INF/temp" );
//		File tempDir = new File( tempPath );
//		if ( ! tempDir.exists() ) 
//			tempDir.mkdirs();
//		return tempDir.getPath();
//	}
	
	/**
	 * 获取远程IP地址
	 * @return
	 */
	protected String getRemoteAddr(){
		return request.getRemoteAddr();
	}

	/**
	 * 获取资源文件中的信息
	 * @param name
	 * @param args
	 * @return
	 */
	protected String getMessage( String name, Object... args ) {
		return source.message( name, args );
	}
	
	/**
	 * 返回消息给前端
	 * @param message
	 * @return
	 */
	protected Message improve(Message message ) {
		if ( null == message ) {
            message = new Message( Status.PARAM_INVALID );
        }

		// 若消息中没有message, 那么就从MessageSource中获取对应code的消息内容
		if ( null == message.getMessage() ) {
            message.setMessage( this.getMessage(
                    String.valueOf( message.getCode() ) ) );
        }

		return message;
	}
	
	protected Message getMessage( Status status ) {
		return improve( new Message( status ) );
	}

	/**
	 * 返回验证失败的第一个错误信息
	 * @param errors
	 * @return
	 */
	protected Message getMessage( Errors errors ) {
		FieldError error = errors.getFieldErrors().get(0);
		Object[] arguments = error.getArguments();
		if ( arguments.length > 0 ) {
			Object[] dest = new Object[ arguments.length - 1 ];
			System.arraycopy( arguments, 1, dest, 0, dest.length );
			arguments = dest;
		}
		String message = this.getMessage( error.getDefaultMessage(), arguments );
		return new Message( Status.PARAM_INVALID, message );
	}
	
}
