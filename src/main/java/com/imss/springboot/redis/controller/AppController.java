package com.imss.springboot.redis.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.imss.springboot.redis.model.Employee;
import com.imss.springboot.redis.respository.EmployeeRespository;
import com.imss.springboot.redis.service.CountService;
import com.imss.springboot.redis.service.CountService2;

@RestController
public class AppController {

	@Autowired
	private EmployeeRespository empRepository;
	@Autowired
	private CountService countService;
	
	@Autowired
	private CountService2 countService2;
	
	@RequestMapping("/getAllEmp")
	public ResponseEntity<?> getAllEmp()
	{
		Map<Integer, Employee> employees = empRepository.getAllEmp();
		if(employees.isEmpty()) {
			return new ResponseEntity<>("No Data Found in the DB.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Map<Integer,Employee>>(employees, HttpStatus.OK);
		
	}
	
	@RequestMapping("/getEmp/{empid}")
	public ResponseEntity<?> getEmp(@PathVariable int empid)
	{
		Employee oneEmployee = empRepository.getOneEmployee(empid);
		if(oneEmployee == null)
		{
			return new ResponseEntity<>("The Employee Data is not found in DB", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(oneEmployee, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/updateEmp/{empId}")
	public ResponseEntity<?> updateEmp(@RequestBody Employee employee, @PathVariable int empId)
	{
		if(employee != null)
		{
			boolean updateFlag=empRepository.updateEmp(employee, empId);
			if(updateFlag)
			{
				return new ResponseEntity<>(employee, HttpStatus.OK);
			}
			return new ResponseEntity<>("The Employee ID is not present in the DB", HttpStatus.OK);
		}
		return new ResponseEntity<>("Please provide valid details of Employee.", HttpStatus.BAD_REQUEST);
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/deleteEmp/{empId}")
	public ResponseEntity<String> deleteEmp(@PathVariable int empId)
	{
		Long deleteEmp = empRepository.deleteEmp(empId);
		if(deleteEmp != 0) {
		return new ResponseEntity<>("The Employee Deleted Successfully", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("The Employee ID is not present in the DB.", HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/addEmp")
	@ResponseBody
	public ResponseEntity<String> addEmployee(@RequestBody Employee employee)
	{
		empRepository.saveEmp(employee);
		return new ResponseEntity<>("The Employee Added Successfully", HttpStatus.OK);
	}	
	
	@RequestMapping("/addMsisdn/{msisdn}")
	public void addMsisdn(@PathVariable String msisdn)
	{
		countService.recordMobNumHits(msisdn);
	}
	
	@RequestMapping("/getCount/{msisdn}")
	public Long test(@PathVariable String msisdn)
	{
		LocalDate date = LocalDate.now();
		return countService.getHitsCount(msisdn, date);	
	}
	
	@RequestMapping("/addingMsisdn/{msisdn}")
	public void addingMsisdn(@PathVariable String msisdn)
	{
		countService2.recordMsisdnHit(msisdn);
	}
	
	@RequestMapping("/getCounting/{msisdn}")
	public Long count(@PathVariable String msisdn)
	{
		return countService2.getMobileHitCount(msisdn);
	}
}
