package com.adou.springboot.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.adou.springboot.mybatis.util.MyMapper;
import com.google.common.base.Preconditions;
/**
 * 数据操作基类
 * @author zhoudoujun01
 *
 * @param <T>
 * @date 2019年8月6日15:08:30
 */
@Service
public abstract class BaseService<T> {
	/**
	 * 数据操作基类mapper
	 */
    @Autowired
    protected MyMapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }
    
    /**
     * 查询
     * @param key
     * @return
     */
    public T get(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    /**
     * 添加
     * @param entity
     */
    public void add(T entity) {
        Preconditions.checkNotNull(entity);
        mapper.insertUseGeneratedKeys(entity);
    }

    /**
     * 添加
     * @param list
     * @return
     */
    public int adds(List<T> list) {
        Preconditions.checkNotNull(list);
        return mapper.insertList(list);
    }

    /**
     * 删除
     * @param key
     * @return
     */
    public int delete(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    /**
     * 更新
     * @param entity
     * @return
     */
    public int update(T entity) {
        Preconditions.checkNotNull(entity);
        return mapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 查询
     * @param example
     * @return
     */
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }
}
