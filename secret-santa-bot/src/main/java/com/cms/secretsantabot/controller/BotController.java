package com.cms.secretsantabot.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.cms.secretsantabot.model.Employee;
import com.cms.secretsantabot.services.EmployeeService;
import com.cms.secretsantabot.services.SecretNameGeneratingService;


@Slf4j
@RestController

@RequestMapping(value = "/api/secretSanta", produces = MediaType.APPLICATION_JSON_VALUE)
public class BotController {

 @Autowired
	EmployeeService employeeService;


  @Autowired
	SecretNameGeneratingService nameGeneratingService;

	@GetMapping(value = "/employees")
	public List<Employee> getAllEmployees() {
		System.out.println("inside getAllEmployees");
		return employeeService.getAllEmployees();
	}


	@GetMapping(value = "/reports/generate")
	public StreamingResponseBody generateReport(HttpServletResponse response) {
		System.out.println("inside generate report");
		return employeeService.generateReport(response);
	}

	@GetMapping(value = "/employees/{empId}")
	public Employee findById(@PathVariable("empId") int id) {
		System.out.println("inside find by id:"+ id);
		return employeeService.getEmployeesById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/employees",method = RequestMethod.POST)
	public Employee addEmployee(@RequestBody Employee employee) {
		System.out.println("inside add record:"+ employee);
		return employeeService.addEmployee(employee);
	}

	@RequestMapping(value = "/employees",method = RequestMethod.PUT)
	public Employee updateEmployee(@RequestBody Employee employee) {
		System.out.println("inside update record:"+ employee);
		return employeeService.updateEmployee(employee);
	}



	@GetMapping(value = "/getAllMatchedEmployees")
	public List<Employee> getAllMatchedEmployees() {
		log.info("Generation logic : controller");
		nameGeneratingService.saveAllMatchedEmployees();
		return employeeService.getAllEmployees();
	}

}
