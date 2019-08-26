package com.adou.springboot.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adou.springboot.mybatis.mapper.PersonMapper;
import com.adou.springboot.mybatis.model.Person;

@Service
public class PersonService {
	@Autowired
    private PersonMapper personMapper;
	
    public Person selectById(String pid) {
        return personMapper.selectPersonById(pid);
    }
    
    
    public int insert(Person person) {
    	return personMapper.insert(person);
    }
}
