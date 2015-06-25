package com.madhouse.fmp.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		//return new Class<?>[] { MainConfig.class, WebMvcConfig.class, SecurityConfig.class, SocialConfig.class };
		return new Class<?>[] { SocialConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] { "/" };
	}

    @Override                                                                                                                                                
    protected Filter[] getServletFilters() {                                                                                                                 
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
                                                                                                                                                             
        DelegatingFilterProxy reconnectDelegate = new DelegatingFilterProxy("apiExceptionHandler");                                                                                                                                                             
        return new Filter[] { reconnectDelegate, encodingFilter, new HiddenHttpMethodFilter() };
    }


}
