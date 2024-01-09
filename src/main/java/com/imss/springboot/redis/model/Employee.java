package com.imss.springboot.redis.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import jakarta.annotation.sql.DataSourceDefinition;
import lombok.AllArgsConstructor;

@RedisHash("Employee")
public class Employee implements Serializable {
 
	@Id
	@Indexed
	private int id;
	private String name;
	private String designation;
	//private String msisdn;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
}
