package com.adou.springboot.mybatis;


import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * @author zhoudoujun01
 * @date 2018年11月8日09:46:14
 * @Description:工程启动
 */
@SpringBootApplication(exclude = MybatisAutoConfiguration.class)
@EnableTransactionManagement
public class SBMApplication extends SpringBootServletInitializer {

	/**
	 * 启动环境配置
	 */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(SBMApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SBMApplication.class, args);
    }
}
