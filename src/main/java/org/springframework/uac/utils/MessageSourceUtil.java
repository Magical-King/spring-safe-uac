package org.springframework.uac.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * @author Sir.D
 */
@Configuration
public class MessageSourceUtil {

    @Resource
    private MessageSource source;

    private Locale locale;

    public String message(int code) {
        return source.getMessage(String.valueOf(code), null, locale);
    }
    public String message(String code) {
        return source.getMessage(code, null, locale);
    }

    public String message( int code, Object... args ) {
        return source.getMessage( String.valueOf(code), args, locale );
    }

    public String message( String name, Object... args ) {
        return source.getMessage( name, args, locale );
    }

    public void setLanguage(Locale language) {
        locale = language;
    }
}
