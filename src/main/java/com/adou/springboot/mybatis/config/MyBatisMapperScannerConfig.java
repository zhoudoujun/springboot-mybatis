package com.adou.springboot.mybatis.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
@AutoConfigureAfter(SqlSessionFactoryConfig.class)
public class MyBatisMapperScannerConfig {
	private Logger log = LoggerFactory.getLogger(MyBatisMapperScannerConfig.class);

	private String notEmpty = "false";

	private String identity = "MYSQL";

	private String mysqlBasePackage = "com.adou.springboot.mybatis.mapper";

	/**
	 * Mybatis扫描Mysql库相关的SQL文件的配置
	 * 
	 * @return MapperScannerConfigurer对象
	 */
	@Bean(name = "mysqlMapperScanner")
	public MapperScannerConfigurer mysqlMapperScannerConfigurer() {
		log.info("mysql mapper 开始扫描" + mysqlBasePackage);
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("mysqlSqlSessionFactory");
		mapperScannerConfigurer.setBasePackage(mysqlBasePackage);
		Properties properties = new Properties();
		properties.setProperty("notEmpty", notEmpty);
		properties.setProperty("IDENTITY", identity);
		mapperScannerConfigurer.setProperties(properties);
		log.info("mysql mapper 加载完成");
		return mapperScannerConfigurer;
	}
}
