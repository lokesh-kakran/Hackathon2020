package com.cms.secretsantabot.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employees")
@Getter
@Setter
@AllArgsConstructor
public class Employee {
	@Id
	private int id;
	private String empName;
	private String emailAddress;
	private Map<String, String> wishList;
	private int toEmpId;
	public Employee() {
	}

	@Override
	public String toString() {
		return String.format(
				"Employee[id=%s, empName='%s', emailAddress='%s', wishList='%s']",
				id, empName, emailAddress, wishList.toString());
	}

}
