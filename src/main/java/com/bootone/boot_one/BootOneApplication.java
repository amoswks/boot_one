package com.bootone.boot_one;


import com.bootone.boot_one.filter.HttpFilter;
import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import javax.servlet.FilterRegistration;
//import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = PageHelperAutoConfiguration.class)
@EnableCaching// 开启缓存，需要显示的指定
//@ComponentScan(basePackages={"com.bootone.boot_one.Controller","com.bootone.boot_one.service","com.bootone.boot_one.dao"})
@MapperScan("com.bootone.boot_one.dao.*")
public class BootOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootOneApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean httpFilter(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new HttpFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
}
