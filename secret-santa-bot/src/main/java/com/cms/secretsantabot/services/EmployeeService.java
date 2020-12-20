package com.cms.secretsantabot.services;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.cms.secretsantabot.model.Employee;

public interface EmployeeService {
	public List<Employee> getAllEmployees();
	public Employee getEmployeesById(int id);
	public Employee addEmployee(Employee employee);
	public Employee updateEmployee(Employee employee);
	public ResponseEntity<byte[]> generateReport(HttpServletResponse response);
}
