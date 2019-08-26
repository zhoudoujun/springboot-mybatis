package com.adou.springboot.mybatis.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adou.springboot.mybatis.common.constants.ResponseDTO;
import com.adou.springboot.mybatis.common.controller.BaseController;
import com.adou.springboot.mybatis.model.Person;
import com.adou.springboot.mybatis.service.PersonService;


@RestController
@RequestMapping("/person")
public class PersonController extends BaseController{
	private static final Logger logger = LogManager.getLogger(PersonController.class);

	@Autowired
	private PersonService personService;

	@PostMapping(value = "/selectById")
	public ResponseDTO<?> selectById(String pid) {
		logger.info("====================== pid: " + pid);
		Person  person = personService.selectById(pid);
		ResponseDTO<?> response = onSuccess(person);
		logger.info("====================== response: " + response);
		return response;
	}

}
