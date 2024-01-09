package com.imss.springboot.redis.respository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.imss.springboot.redis.model.Employee;

@Repository
public class EmployeeRespository  {

	public static final String KEY= "Employee";
	
	
	private RedisTemplate<String, Employee> redisTemplate;
	private HashOperations hashOperations;
	
	public EmployeeRespository(RedisTemplate<String, Employee> redisTemplate) {
		this.hashOperations = redisTemplate.opsForHash();
		this.redisTemplate = redisTemplate;
	}
	
	public void saveEmp(Employee employee)
	{
	     hashOperations.put(KEY, employee.getId(), employee);
	}
	
	public Map<Integer, Employee> getAllEmp()                    
	{
		return hashOperations.entries(KEY);
	}
	
	public Employee getOneEmployee(int empId)
	{
		return (Employee) hashOperations.get(KEY, empId);	
	}
	
	public Long deleteEmp(int empId)
	{
		return hashOperations.delete(KEY, empId);
	}
	
	public boolean updateEmp(Employee employee, int id)
	{
		Employee empDetails = (Employee) hashOperations.get(KEY, id);
		if(empDetails != null)
		{
			saveEmp(employee);
			return true;
		}
		return false;
	}
	
}
