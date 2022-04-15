package com.apress.prospring5.ch16.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Locale;

@Configuration
@ComponentScan(basePackages = "com.apress.prospring5.ch16")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer /*extends WebMvcConfigurerAdapter*/ {

    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

//    @Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
//        //resource.setBasenames("translate", "messages", "application");
//        resource.setBasenames("WEB-INF/i18n/translate","WEB-INF/i18n/messages", "WEB-INF/i18n/application", "WEB-INF/i18n/translate");
//        return resource;
//    }
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("WEB-INF/i18n/messages", "WEB-INF/i18n/application", "WEB-INF/i18n/translate");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInteceptor = new LocaleChangeInterceptor();
        localeChangeInteceptor.setParamName("language");
        registry.addInterceptor(localeChangeInteceptor);
    }


    @Bean
    public CookieLocaleResolver localeResolver(){
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        localeResolver.setCookieName("my-locale-cookie");
        localeResolver.setCookieMaxAge(3600);
        return localeResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/")
                .setCachePeriod(31556926);
    }

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

//    private final ApplicationContext applicationContext;
//
//    @Autowired
//    public SpringConfig(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
//
//    @Bean
//    public ViewResolver internalResourceViewResolver() {
//        InternalResourceViewResolver bean = new InternalResourceViewResolver();
//        //bean.setViewClass(JstlView.class);
//        bean.setPrefix("/WEB-INF/views/");
//        bean.setSuffix(".jsp");
//        return bean;
//    }
//
//
//    @Bean
//    public ResourceBundleMessageSource messageSource() {
//
//        var source = new ResourceBundleMessageSource();
//        //source.setBasenames("WEB-INF/i18n/messages", "WEB-INF/i18n/application");
//        source.setBasenames("i18n/messages", "i18n/application");
//        source.setUseCodeAsDefaultMessage(true);
//
//        return source;
//    }
//
//    @Bean
//    CookieLocaleResolver localeResolver() {
//        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
//        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
//        cookieLocaleResolver.setCookieMaxAge(3600);
//        cookieLocaleResolver.setCookieName("locale");
//        return cookieLocaleResolver;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        LocaleChangeInterceptor localeChangeInteceptor = new LocaleChangeInterceptor();
//        localeChangeInteceptor.setParamName("lang");
//        registry.addInterceptor(localeChangeInteceptor);
//    }
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeChangeInterceptor());
//        //registry.addInterceptor(themeChangeInterceptor());
//        registry.addInterceptor(webChangeInterceptor());
//    }
//
//    @Bean
//    LocaleChangeInterceptor localeChangeInterceptor() {
//        return new LocaleChangeInterceptor();
//    }

//    @Bean
//    WebContentInterceptor webChangeInterceptor() {
//        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
//        webContentInterceptor.setCacheSeconds(0);
//        webContentInterceptor.setSupportedMethods("GET", "POST", "PUT", "DELETE");
//        return webContentInterceptor;
//    }
//    @Bean
//    public ReloadableResourceBundleMessageSource messageSource() {
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasenames("WEB-INF/i18n/messages", "WEB-INF/i18n/application");
//        messageSource.setDefaultEncoding("UTF-8");
//        messageSource.setFallbackToSystemLocale(false);
//        return messageSource;
//    }

//    @Bean
//    public SpringResourceTemplateResolver templateResolver() {
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        templateResolver.setApplicationContext(applicationContext);
//        templateResolver.setPrefix("/WEB-INF/views/");
//        templateResolver.setSuffix(".html");
//        return templateResolver;
//    }

//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//        templateEngine.setEnableSpringELCompiler(true);
//        return templateEngine;
//    }
//
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//        resolver.setTemplateEngine(templateEngine());
//        registry.viewResolver(resolver);
//    }

}
