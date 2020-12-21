package com.cms.secretsantabot.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cms.secretsantabot.model.Admin;


@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
    @Override
    public List<Admin> findAll();

    public Admin getAdminByUsername(String username);

    @Override
    public Admin save(Admin admin);

}
