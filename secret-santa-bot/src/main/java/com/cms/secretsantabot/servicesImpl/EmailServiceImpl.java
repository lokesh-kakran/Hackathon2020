package com.cms.secretsantabot.servicesImpl;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cms.secretsantabot.Util.CommonConstants;
import com.cms.secretsantabot.model.Employee;
import com.cms.secretsantabot.repository.EmployeeRepository;
import com.cms.secretsantabot.services.EmailService;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

	@Autowired
	private Environment environment;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendEmailstoEmployee(List<Employee> employees) {
		employees.stream().forEach(employee -> {
			Employee toEmployee = employeeRepository.getEmployeesById(employee.getToEmpId());
			List<String> wishlist = new ArrayList<>();
			toEmployee.getWishList().forEach((s, wish) -> wishlist.add(wish));
			String messageBody = String.format(CommonConstants.MESSAGE_BODY, employee.getEmpName().toUpperCase(), toEmployee.getEmpName(), wishlist.get(0), wishlist.get(1), wishlist.get(2), environment.getProperty("admin.name"));
			SimpleMailMessage simpleMailMessage = composeEmail("Merry Christmas", messageBody, employee);
			log.info("Sending Email to={}", employee.getEmpName());
			mailSender.send(simpleMailMessage);
		});
	}

	public SimpleMailMessage composeEmail(String subject, String messageText, Employee employee) {
		final SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject(subject);
		message.setText(messageText);
		message.setTo(employee.getEmailAddress());
		message.setFrom(environment.getProperty("customer.care.email"));
		return message;
	}
}

