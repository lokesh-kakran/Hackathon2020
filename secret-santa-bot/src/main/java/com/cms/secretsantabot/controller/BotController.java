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

import com.cms.secretsantabot.model.Employee;
import com.cms.secretsantabot.services.EmailService;
import com.cms.secretsantabot.services.EmployeeService;
import com.cms.secretsantabot.services.SecretNameGeneratingService;


@Slf4j
@RestController

@RequestMapping(value = "/api/secretSanta", produces = MediaType.APPLICATION_JSON_VALUE)
public class BotController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	EmailService emailService;

	@Autowired
	SecretNameGeneratingService nameGeneratingService;

	@GetMapping(value = "/employees")
	public List<Employee> getAllEmployees() {
		log.info("inside getAllEmployees");
		return employeeService.getAllEmployees();
	}


	@GetMapping(value = "/reports/generate")
	public StreamingResponseBody generateReport(HttpServletResponse response) {
		System.out.println("inside generate report");
		return employeeService.generateReport(response);
	}

	@GetMapping(value = "/employees/{empId}")
	public Employee findById(@PathVariable("empId") int id) {
		log.info("inside find by id:{}", id);
		return employeeService.getEmployeesById(id);
	}

	@GetMapping(value = "/getAllMatchedEmployees")
	@ResponseStatus(HttpStatus.OK)
	public List<Employee> getAllMatchedEmployees() {
		log.info("Generation logic : controller");
		nameGeneratingService.saveAllMatchedEmployees();
		return employeeService.getAllEmployees();
	}

	@GetMapping(value = "/reports/generate")
	public ResponseEntity<byte[]> generateReport(HttpServletResponse response) {
		System.out.println("inside generate report");
		return employeeService.generateReport(response);
	}

	@GetMapping(value = "/sendEmails")
	@ResponseStatus(HttpStatus.OK)
	public void sendEmails() {
		log.info("inside sendEmails");
		List<Employee> list = employeeService.getAllEmployees();
		emailService.sendEmailstoEmployee(list);
	}
}
