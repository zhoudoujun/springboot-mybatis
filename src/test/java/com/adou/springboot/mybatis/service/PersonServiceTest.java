package com.adou.springboot.mybatis.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.adou.springboot.mybatis.common.bo.DataSourceId;
import com.adou.springboot.mybatis.model.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {
	private static final Logger log = LoggerFactory.getLogger(PersonServiceTest.class);
	
	@Autowired
	private PersonService personService;
	
	@Test
	public void insert() {
		Person person = new Person();
		
		String pid = DataSourceId.buildID();
		System.out.println("========================== " + pid);
		person.setId(2);
		person.setPid(pid);
		person.setName("王麻子");
		person.setAge(20);
		
		personService.insert(person);
	}
}
