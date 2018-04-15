package com.itmuch.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class MicroserviceSimpleProviderUserApplication  {

//	/**
//	   * 1、 extends WebMvcConfigurationSupport
//	   * 2、重写下面方法; 	String classPath=this.getClass().getClassLoader().getResource("config.json").getPath();
//	   * setUseSuffixPatternMatch : 设置是否是后缀模式匹配，如“/user”是否匹配/user.*，默认真即匹配；
//	   * setUseTrailingSlashMatch : 设置是否自动后缀路径模式匹配，如“/user”是否匹配“/user/”，默认真即匹配；
//	   */
//	  @Override
//	  public void configurePathMatch(PathMatchConfigurer configurer) {
//	    configurer.setUseSuffixPatternMatch(false)
//	          .setUseTrailingSlashMatch(true);
//	  }
//	  @Override
//	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//	        return application.sources(MicroserviceSimpleProviderUserApplication.class);
//	    }

	public static void main(String[] args) {
		
		SpringApplication.run(MicroserviceSimpleProviderUserApplication.class, args);
	}
}
