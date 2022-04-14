package com.hongdatchy.bikeshare.serviceImpl;

import com.hongdatchy.bikeshare.entities.model.Admin;
import com.hongdatchy.bikeshare.entities.request.LoginForm;
import com.hongdatchy.bikeshare.repo.AdminRepoJpa;
import com.hongdatchy.bikeshare.security.SHA256Service;
import com.hongdatchy.bikeshare.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepoJpa adminRepoJpa;

    @Override
    public Admin login(LoginForm loginForm) {
        Admin admin = Admin.builder()
                .email(loginForm.getEmail())
                .password(SHA256Service.getSHA256(loginForm.getPassword()))
                .build();
        Example<Admin> adminExample = Example.of(admin, ExampleMatcher.matchingAll());
        List<Admin> admins = adminRepoJpa.findAll(adminExample);
        return admins.size() == 1 ? admins.get(0) : null;
    }
}
