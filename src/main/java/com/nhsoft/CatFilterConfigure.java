package com.nhsoft;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Created by yangqin on 2017/9/26.
 */
//@Configuration
public class CatFilterConfigure {
	
	@Bean
	public FilterRegistrationBean catFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
//		CatFilter filter = new CatFilter();
//		registration.setFilter(filter);
//		registration.addUrlPatterns("/*");
//		registration.setName("cat-filter");
//		registration.setOrder(2);
		return registration;
	}
}
