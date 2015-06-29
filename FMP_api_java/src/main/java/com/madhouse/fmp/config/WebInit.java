package com.madhouse.fmp.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

public class WebInit{
//public class WebInit implements WebApplicationInitializer {
//
//	@Override
//	public void onStartup(ServletContext servletContext)
//			throws ServletException {
//
//		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
//		appContext.setDisplayName("FMP");
//		appContext.register(MainConfig.class, SocialConfig.class,
//				WebMvcConfig.class, SpringMvcInitializer.class);
//		servletContext.addListener(new ContextLoaderListener(appContext));
//		// servletContext.addFilter("encode", CharacterEncodingFilter.class)
//		// .addMappingForUrlPatterns(null, false, "/*");
//
//	}

}
