package com.adou.springboot.mybatis.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@AutoConfigureAfter(ShardingDataSourceConfig.class)
public class SqlSessionFactoryConfig {
	private static final Logger log = LoggerFactory.getLogger(SqlSessionFactoryConfig.class);
	/**
	 * 扫描 Mapper 接口并容器管理
	 */
	static final String MAPPER_LOCATION_MYSQL = "classpath*:mapper/mysql/*.xml";
	@Resource(name = "shardingDataSource")
	private DataSource shardingDataSource;

	@Bean(name = "transactionManager")
	// @Primary
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(shardingDataSource);
	}

	@Bean(name = "mysqlSqlSessionFactory")	
	//@Primary
	public SqlSessionFactory sqlSessionFactory(@Qualifier("shardingDataSource") DataSource shardingDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(shardingDataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources(SqlSessionFactoryConfig.MAPPER_LOCATION_MYSQL));
		return sessionFactory.getObject();
	}

	@Bean(name = "mysqlSqlSession")
	// @ConditionalOnMissingBean
	public SqlSessionTemplate mysqlSqlSessionTemplate(
			@Qualifier(value = "mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.SIMPLE);
	}
	
	/**
	 * 分页插件
	 */
	// @Bean
	// public PageHelper pageHelper(DataSource dataSource) {
	// log.info("注册MyBatis分页插件PageHelper");
	// PageHelper pageHelper = new PageHelper();
	// Properties p = new Properties();
	// p.setProperty("offsetAsPageNum", "true");
	// p.setProperty("rowBoundsWithCount", "true");
	// p.setProperty("reasonable", "true");
	// pageHelper.setProperties(p);
	// return pageHelper;
	// }
}
