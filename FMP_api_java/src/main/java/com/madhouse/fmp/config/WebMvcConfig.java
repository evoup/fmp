package com.madhouse.fmp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.madhouse.fmp")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/resources/");
	}
	

//	@Bean
//	public MappingJackson2HttpMessageConverter jsonHttpMessageConverter() {
//		MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
//		return jsonHttpMessageConverter;
//	}
	
//	@Override
//	public void configureMessageConverters(
//			List<HttpMessageConverter<?>> converters) {
//		converters.add(jsonHttpMessageConverter());
//		super.configureMessageConverters(converters);
//	}
	
//	/** This tag allows for mapping the DispatcherServlet to "/" */
//	 @Override
//	 public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//	        configurer.enable();
//	    }
	
	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false).useJaf(false)
				.ignoreAcceptHeader(true)
				.mediaType("html", MediaType.TEXT_HTML)
				.mediaType("json", MediaType.APPLICATION_JSON)
				.defaultContentType(MediaType.APPLICATION_JSON);
	}
	
	@Bean  
    public ViewResolver viewResolver() {  
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();  
        viewResolver.setPrefix("/WEB-INF/jsp/");  
        viewResolver.setSuffix(".jsp");  
        return viewResolver;  
    } 
	
	// @Bean
	// public MessageSource messageSource() {
	// ReloadableResourceBundleMessageSource messageSource = new
	// ReloadableResourceBundleMessageSource();
	// messageSource.setBasename("/WEB-INF/messages/messages");
	// return messageSource;
	// }
	//
	// @Bean
	// public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
	// ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	// viewResolver.setTemplateEngine(templateEngine);
	// return viewResolver;
	// }
	//
	// @Bean
	// public SpringTemplateEngine templateEngine(TemplateResolver
	// templateResolver) {
	// SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	// templateEngine.setTemplateResolver(templateResolver);
	// templateEngine.addDialect(new SpringSocialDialect());
	// templateEngine.addDialect(new LayoutDialect());
	// return templateEngine;
	// }
	//
	// @Bean
	// public TemplateResolver templateResolver() {
	// TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	// templateResolver.setPrefix("/views/");
	// templateResolver.setSuffix(".html");
	// templateResolver.setTemplateMode("HTML5");
	// return templateResolver;
	// }
}
