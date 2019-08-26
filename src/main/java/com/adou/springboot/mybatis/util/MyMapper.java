package com.adou.springboot.mybatis.util;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承自己的MyMapper
 * 
 * @author zhoudoujun01
 *
 * @param <T>
 * @date 2019年8月6日15:05:51
 */
@RegisterMapper
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}
