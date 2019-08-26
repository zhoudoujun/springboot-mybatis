package com.adou.springboot.mybatis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

@Table(name = "person")
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7764304388265446959L;

	@Id
	@Column(name = "ID")
	@KeySql(dialect = IdentityDialect.MYSQL)
	private Integer id;

	@Column(name = "pid")
	private String pid;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "age")
	private Integer age;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", pid=" + pid + ", name=" + name + ", age=" + age + "]";
	}
}
