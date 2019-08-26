package com.adou.springboot.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.adou.springboot.mybatis.model.Person;

/**
 * 
 * @author zhoudoujun01
 *
 */
@Mapper
public interface PersonMapper {

    Person selectPersonById(String pid);

    List<Person> selectAll();

    int insert(Person person);

    Long update(Person person);

    Long delete(Long id);
}
