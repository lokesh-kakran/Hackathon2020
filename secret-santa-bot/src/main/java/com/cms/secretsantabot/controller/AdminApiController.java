package com.cms.secretsantabot.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cms.secretsantabot.model.Admin;
import com.cms.secretsantabot.model.Employee;
import com.cms.secretsantabot.services.AdminService;
import com.cms.secretsantabot.services.EmailService;
import com.cms.secretsantabot.services.EmployeeService;
import com.cms.secretsantabot.services.SecretNameGeneratingService;


@Slf4j
@RestController

@RequestMapping(value = "/api/secretSanta/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminApiController {

	@Autowired
	AdminService adminService;

	@GetMapping(value = "/")
	public List<Admin> getAllEmployees() {
		log.info("inside getAllAdmins");
		return adminService.getAllAdmins();
	}

	@GetMapping(value = "/{username}")
	public Admin findById(@PathVariable("username") String username) {
		log.info("inside admin by username:{}", username);
		return adminService.getAdminById(username);
	}
}
