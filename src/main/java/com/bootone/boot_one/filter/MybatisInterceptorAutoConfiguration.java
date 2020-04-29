package com.bootone.boot_one.filter;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

@Configuration
public class MybatisInterceptorAutoConfiguration {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @Bean
    @ConfigurationProperties(prefix = "pagehelper")
    public Properties pageHelperProperties() {
        return new Properties();
    }

    @PostConstruct
    public void addMysqlInterceptor() {
        //数据权限拦截器
        QueryByAppInterceptor dataPermissionInterceptor = new QueryByAppInterceptor();
        //分页拦截器
        PageInterceptor pageInterceptor = new PageInterceptor();
         pageInterceptor.setProperties(this.pageHelperProperties());

        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
//
            sqlSessionFactory.getConfiguration().addInterceptor(pageInterceptor);
            sqlSessionFactory.getConfiguration().addInterceptor(dataPermissionInterceptor);
        }
    }
}
