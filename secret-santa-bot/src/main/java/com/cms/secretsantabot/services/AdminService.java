package com.cms.secretsantabot.services;

import java.util.List;

import com.cms.secretsantabot.model.Admin;

public interface AdminService {
	public List<Admin> getAllAdmins();
	public Admin getAdminById(String username);
}
