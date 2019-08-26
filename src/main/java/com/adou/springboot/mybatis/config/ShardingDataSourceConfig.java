package com.adou.springboot.mybatis.config;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.ComplexShardingStrategyConfiguration;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.adou.springboot.mybatis.common.DatabaseShardingAlgorithm;
import com.google.common.collect.Maps;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * sharding配置:分表
 * 
 * @author zhoudoujun01
 * @date 2019年8月6日14:31:23
 */
@Configuration
@ConfigurationProperties
@PropertySource(value = { "classpath:jdbc2.properties" })
public class ShardingDataSourceConfig {
	private Logger log = LoggerFactory.getLogger(ShardingDataSourceConfig.class);

	@Autowired
	private Environment env;

	/**
	 * 分库表名 PERSON
	 */
	private static final String LOGIC_TABLE_PERSON = "PERSON";

	/**
	 * PERSON表规则
	 */
	private static final String ACTUAL_DATA_NODES_PERSON = "ds_0${0..3}." + LOGIC_TABLE_PERSON;

	/**
	 * 分库列名
	 */
	private static final String SHARDING_COLUMN_ID = "pid";

	/**
	 * 主键生成列
	 */
	private static final String KEY_GENERATOR_COLUMN_NAME_ID = "id";

	/**
	 * mysql数据源获取前缀
	 */
	private static final String MYSQL_DS_PREFIX = "test";

	/**
	 * mysql数据源获取后缀
	 */
	private static final String MYSQL_DS_SUFFIX_URL = ".url";

	/**
	 * 系统环境名
	 */
	private static final String ACTIVE_ENV = "spring.profiles.active";

	/**
	 * 多数据源别名列表
	 */
	private static String[] DS_NAMES = { "ds_00", "ds_01", "ds_02", "ds_03" };

	/**
	 * 数据源集成配置
	 * 
	 * @return
	 * @throws SQLException
	 */
	@Bean
	@Primary
	public DataSource shardingDataSource() throws SQLException {
		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		// PERSON表分库规则
		shardingRuleConfig.getTableRuleConfigs().add(getPersonRuleConfiguration());

		return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new HashMap(), new Properties());
	}

	/**
	 * 定义Person表分库规则
	 * 
	 * @return
	 */
	private TableRuleConfiguration getPersonRuleConfiguration() {
		TableRuleConfiguration paymentRequestTableRule = new TableRuleConfiguration();
		paymentRequestTableRule.setLogicTable(LOGIC_TABLE_PERSON);
		paymentRequestTableRule.setActualDataNodes(ACTUAL_DATA_NODES_PERSON);
		paymentRequestTableRule.setDatabaseShardingStrategyConfig(new ComplexShardingStrategyConfiguration(SHARDING_COLUMN_ID, DatabaseShardingAlgorithm.class.getName()));
		paymentRequestTableRule.setKeyGeneratorColumnName(KEY_GENERATOR_COLUMN_NAME_ID);
		return paymentRequestTableRule;
	}

	/**
	 * 组装数据源map
	 * 
	 * @return
	 */
	private Map<String, DataSource> createDataSourceMap() {
		HashMap<String, DataSource> dataSourceMap = Maps.newHashMap();
		String activeEnvPrd = "prd";
		// 得到系统环境
		String activeEnv = env.getProperty(ACTIVE_ENV);
		log.info("dataSource 1 url:{} activeEnv:" + activeEnv + "," + env.getProperty(MYSQL_DS_PREFIX + 1 + MYSQL_DS_SUFFIX_URL));

		// 未配置或者指定生产环境时，直接使用指定数据源配置
		if (activeEnv == null || activeEnvPrd.equalsIgnoreCase(activeEnv)) {
			for (int i = 1; i <= DS_NAMES.length; i++) {
				dataSourceMap.put(DS_NAMES[i - 1], getDataSourceItem(i));
			}
		}
		// 测试环境下，数据源00，01对应1数据库，数据源02，03对应2数据库
		else {
			for (int i = 1; i <= DS_NAMES.length; i++) {
				if (i <= 2) {
					dataSourceMap.put(DS_NAMES[i - 1], getDataSourceItem(1));
				} else if (i <= 4) {
					dataSourceMap.put(DS_NAMES[i - 1], getDataSourceItem(2));
				}
			}
		}
		return dataSourceMap;
	}

	/**
	 *  根据指定数据源配置标识生成数据源对象
	 * @param i
	 * @return
	 */
	private DataSource getDataSourceItem(int i) {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(env.getProperty(MYSQL_DS_PREFIX + i + MYSQL_DS_SUFFIX_URL));
		config.setUsername(env.getProperty(MYSQL_DS_PREFIX + i + ".username"));
		config.setPassword(env.getProperty(MYSQL_DS_PREFIX + i + ".password"));
		config.setMinimumIdle(Integer.parseInt(env.getProperty(MYSQL_DS_PREFIX + i + ".minIdle")));
		config.setMaximumPoolSize(Integer.parseInt(env.getProperty(MYSQL_DS_PREFIX + i + ".maxIdle")));
		config.setConnectionTimeout(Long.valueOf(env.getProperty(MYSQL_DS_PREFIX + i + ".maxWait")));
		config.setConnectionTestQuery(env.getProperty(MYSQL_DS_PREFIX + i + ".validationQuery"));
		config.setDriverClassName("com.mysql.jdbc.Driver");

		HikariDataSource ds = new HikariDataSource(config);
		return ds;
	}

}
