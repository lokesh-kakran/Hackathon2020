package com.cms.secretsantabot.servicesImpl;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.secretsantabot.model.Admin;
import com.cms.secretsantabot.repository.AdminRepository;
import com.cms.secretsantabot.services.AdminService;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepository;


	@Override
	public List<Admin> getAllAdmins() {
		return (List<Admin>) adminRepository.findAll();
	}

	@Override
	public Admin getAdminById(String username) {
		return adminRepository.getAdminByUsername(username);
	}
}

