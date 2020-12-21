package com.cms.secretsantabot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admin")
@Getter
@Setter
@AllArgsConstructor
public class Admin {

	@Id
	private String id;
	private String username;
	private String password;
	private boolean assignmentFlag;

	public Admin() {
	}

	@Override
	public String toString() {
		return String.format(
			"Admin[username=%s, password='%s', assignmentFlag='%s']", username, password, assignmentFlag);
	}
}
