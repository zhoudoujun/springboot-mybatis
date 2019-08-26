package com.adou.springboot.mybatis.common;

import io.shardingjdbc.core.api.algorithm.sharding.ListShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.ShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.slf4j.Logger;

import com.adou.springboot.mybatis.common.bo.DataSourceId;
import com.adou.springboot.mybatis.common.constants.Constant;

/**
 * 分库算法：编号后两位</br>
 * 
 * @author zhoudoujun01
 * @date 2019年8月6日14:55:06
 */

public class DatabaseShardingAlgorithm implements ComplexKeysShardingAlgorithm {
	public Collection<String> doSharding(Collection<String> availableTargetNames, Collection<ShardingValue> shardingValues) {
		// 根据分片键名取得分片值列表
		Collection<String> orderIdValues = getShardingValue(shardingValues, Constant.COL_NAME_ID);
		
		//
		Collection<String> dsList = new LinkedHashSet(availableTargetNames.size());

		// 优先按照pid分库
		if (orderIdValues != null && orderIdValues.size() > 0) {
			for (String orderId : orderIdValues) {
				String dsName = getDsNameById(orderId, availableTargetNames);
				if (dsName != null && dsName.length() > 0) {
					dsList.add(dsName);
				}
			}
		}

		if (dsList.size() == 0) {
			throw new UnsupportedOperationException("分库失败（编号不符合规范）");
		}

		return dsList;
	}

	/**
	 * 根据编号取得数据源名：编号后两位
	 * 
	 * @param id
	 * @param availableTargetNames
	 * @return 数据源名
	 */
	private String getDsNameById(String id, Collection<String> availableTargetNames) {
		String dsName = null;
		for (String name : availableTargetNames) {
			// 调用编号规则
			if (name.endsWith(DataSourceId.getDataSourceID(id))) {
				System.out.println("================== id: " + id + ", name: " + name);
				dsName = name;
				break;
			}
		}

		return dsName;
	}

	/**
	 * 根据分片键名取得分片值列表
	 * 
	 * @param shardingValues
	 *            分片值对象
	 * @param key
	 *            分片键名
	 * @return 分片值列表
	 */
	private Collection<String> getShardingValue(Collection<ShardingValue> shardingValues, final String key) {
		Collection<String> valueSet = new ArrayList();
		Iterator<ShardingValue> iterator = shardingValues.iterator();
		while (iterator.hasNext()) {
			ShardingValue next = iterator.next();
			if (next instanceof ListShardingValue) {
				@SuppressWarnings("unchecked")
				ListShardingValue<String> value = (ListShardingValue<String>) next;
				if (value.getColumnName().equals(key)) {
					System.out.println("===================== key: " + key + ", values: " + value.getValues());
					valueSet = value.getValues();
				}
			}
		}
		return valueSet;
	}

}