package com.adou.springboot.mybatis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.util.Locale;

/**
 * @author zhoudoujun01
 * @date:2019年4月24日 下午4:45:08
 * @Description:文本消息配置类。
 */
@Configuration
public class MessageConfiguration {
    /**
     * 消息文本资源
     */
    @Autowired
    private MessageSource messageSource;
    /**
     * 消息文本的缓存时间
     */
    @Value("${spring.messages.cache-seconds}")
    private long cacheMillis;
    
    /**
     * 消息文本编码
     */
    @Value("${spring.messages.encoding}")
    private String encoding;
    
    /**
     * 资源文件名称
     */
    private static final String MESSAGES_VALIDATOR = "classpath:messages-validator";
    
    /**
     * 资源文件名称
     */
    private static final String MESSAGES_RESULT = "classpath:messages-result";

    /**
     * 配置消息资源
     * @return 消息资源对象
     */
    @Primary
    @Bean
    public MessageSource messageResource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(MESSAGES_VALIDATOR, MESSAGES_RESULT);
        messageSource.setDefaultEncoding(encoding);
        messageSource.setCacheMillis(cacheMillis);
        messageSource.setUseCodeAsDefaultMessage(false);
        return messageSource;
    }
    
    /**
     * 设置默认区域解析器（中国）
     * @return 区域解析器
     */
    @Bean
    public LocaleResolver localeResolver() {
        FixedLocaleResolver slr = new FixedLocaleResolver();
        // 默认区域
        slr.setDefaultLocale(Locale.CHINA);
        return slr;
    }
    
    /**
     * 取得消息国际化访问器
     * 
     * @return 消息国际化访问器
     */
    @Bean
    public MessageSourceAccessor messageSourceAccessor() {
//        LocaleContextHolder.setDefaultLocale(Locale.US);
        return new MessageSourceAccessor(messageSource, LocaleContextHolder.getLocale());
    }
    
}
