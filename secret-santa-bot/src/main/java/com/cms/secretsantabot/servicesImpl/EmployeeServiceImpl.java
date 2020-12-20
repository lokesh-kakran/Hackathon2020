package com.cms.secretsantabot.servicesImpl;

import java.util.List;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.cms.secretsantabot.model.Employee;
import com.cms.secretsantabot.repository.EmployeeRepository;
import com.cms.secretsantabot.services.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    //fetch all employees
    public List<Employee> getAllEmployees()  {
        return (List<Employee>) employeeRepository.findAll();
    }

    //Add employee
    public Employee addEmployee(Employee employee)
    {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeesById(int id)
    {
        for ( Employee employee:employeeRepository.findAll()) {
            if (employee.getId() == id)
                return employee;
        }
        return null;
    }

    //Update employee
    public Employee updateEmployee(Employee employee)
    {
        return employeeRepository.save(employee);
    }

    public ResponseEntity<byte[]> generateReport(HttpServletResponse response) {
        byte[] content = getReportContent();
        String fileName = "secret_santa_report.txt";
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(content.length);
        respHeaders.setContentType(new MediaType("text", "plain"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        return new ResponseEntity<byte[]>(content, respHeaders, HttpStatus.OK);
    }

    private byte[] getReportContent() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("EmpId").append(" | ").append("EmpName").append(" | ").append("EmailAddress").append(" | ").append("WishList").append(" | ").append("Assigned EmpId").append("\n");
        for ( Employee employee:employeeRepository.findAll()) {
            stringBuilder.append(employee.getId()).append(" | ").append(employee.getEmpName()).append(" | ").append(employee.getEmailAddress()).append(" | ").append(employee.getWishList().toString()).append(" | ").append(employee.getToEmpId()).append("\n");
        }
        return stringBuilder.toString().getBytes();
    }

    private Employee filterEmployees(Predicate<Employee> strategy) {
        return getAllEmployees().stream().filter(strategy).findFirst().orElse(null);
    }
}
