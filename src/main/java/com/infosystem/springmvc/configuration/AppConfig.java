package com.infosystem.springmvc.configuration;

import com.infosystem.springmvc.Interceptors.JspRestInterceptor;
import com.infosystem.springmvc.converters.JavaUtilDateToStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.infosystem.springmvc")
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public JavaUtilDateToStringConverter javaUtilDateToStringConverter()
    {
        return new JavaUtilDateToStringConverter();
    }

    @Bean
    JspRestInterceptor jspRestInterceptor() {
        return new JspRestInterceptor();
    }

    @Bean
    HandlerExceptionResolver customExceptionResolver () {
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        //mapping spring internal error NoHandlerFoundException to a view name.
        properties.setProperty(NoHandlerFoundException.class.getName(), "404");
        simpleMappingExceptionResolver.setExceptionMappings(properties);
        //This resolver will be processed before default ones
        simpleMappingExceptionResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return simpleMappingExceptionResolver;
    }

    @Override
    public void addFormatters (FormatterRegistry registry) {
        registry.addConverter(javaUtilDateToStringConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jspRestInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/login, /favicon.ico");
        // assuming you put your serve your static files with /resources/ mapping
        // and the pre login page is served with /login mapping
    }

    /**
     * Configure ViewResolvers to deliver preferred views.
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        //viewResolver.setContentType("text/html;charset=UTF-8");
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setExposeContextBeansAsAttributes(true);
        viewResolver.setExposedContextBeanNames("sessionCart");
        registry.viewResolver(viewResolver);
    }

    /**
     * Configure ResourceHandlers to serve static resources like CSS/ Javascript etc...
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/static/images/favicon.ico");
    }

    /*
     * Configure MessageSource to provide internationalized messages
     *
     */
    @Bean
    protected MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

    /**
     * Optional. It's only required when handling '.' in @PathVariables which otherwise ignore everything after last '.' in @PathVaidables argument.
     * It's a known bug in Spring [https://jira.spring.io/browse/SPR-6164], still present in Spring 4.3.0.
     * This is a workaround for this issue.
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer matcher) {
        matcher.setUseRegisteredSuffixPatternMatch(true);
    }
    //

    /**
     * "//" behavior
     */
    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }

}

