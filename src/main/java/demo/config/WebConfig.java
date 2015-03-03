package demo.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"demo.web.controller"})
public class WebConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
  }

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {

    final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("lang");
    registry.addInterceptor(localeChangeInterceptor);
  }

  /**
   * Locale resolution strategy. Defaults to tr.
   * @return locale resolver
   */
  @Bean
  public LocaleResolver localeResolver() {
    CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
    cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("tr"));
    return cookieLocaleResolver;
  }

  /**
   * Thymeleaf template resolver serving HTML 5.
   */
  @Bean
  public ServletContextTemplateResolver templateResolver() {
    ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".html");
    //NB, selecting HTML5 as the template mode.
    resolver.setCharacterEncoding("UTF-8");
    resolver.setTemplateMode("HTML5");
    resolver.setCacheable(false);
    return resolver;

  }

  /**
   * Thymeleaf template engine with Spring integration.
   */
  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine engine = new SpringTemplateEngine();
    engine.setTemplateResolver(templateResolver());
    engine.setMessageSource(messageSource());
    return engine;
  }

  /**
   * Thymeleaf view resolver.
   */
  @Bean
  public ViewResolver viewResolver() {

    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine());
    viewResolver.setOrder(1);
    viewResolver.setViewNames(new String[]{"*"});
    viewResolver.setCache(false);
    viewResolver.setContentType("text/html;charset=UTF-8");
    return viewResolver;
  }

  /**
   * Spring message resolver.
   */
  @Bean
  public MessageSource messageSource() {

    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames("classpath:messages/messages", "classpath:validation/validation");
    // if true, the key of the message will be displayed if the key is not
    // found, instead of throwing a NoSuchMessageException
    messageSource.setUseCodeAsDefaultMessage(true);
    // # -1 : never reload, 0 always reloadw
    messageSource.setCacheSeconds(0);
    return messageSource;
  }

  private static final Charset UTF8 = Charset.forName("UTF-8");

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
    stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "html", UTF8)));
    converters.add(stringConverter);
  }

  @Bean
  public MappingJackson2HttpMessageConverter converter() {
    return new MappingJackson2HttpMessageConverter();
  }

  /**
   * Configure encoding.
   */
  @Bean
  public Filter encodingFilter() {
    CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
    encodingFilter.setEncoding("UTF-8");
    return encodingFilter;
  }

}
