package com.hongdatchy.bikeshare.serviceImpl;

import com.hongdatchy.bikeshare.entities.model.Admin;
import com.hongdatchy.bikeshare.entities.model.User;
import com.hongdatchy.bikeshare.entities.model.UserNotActive;
import com.hongdatchy.bikeshare.entities.request.LoginForm;
import com.hongdatchy.bikeshare.entities.request.RegisterForm;
import com.hongdatchy.bikeshare.repo.AdminRepoJpa;
import com.hongdatchy.bikeshare.repo.UserNotActiveJpa;
import com.hongdatchy.bikeshare.repo.UserRepoJpa;
import com.hongdatchy.bikeshare.security.SHA256Service;
import com.hongdatchy.bikeshare.service.SendEmailService;
import com.hongdatchy.bikeshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepoJpa userRepoJpa;

    @Autowired
    UserNotActiveJpa userNotActiveJpa;

    @Autowired
    AdminRepoJpa adminRepoJpa;

    @Autowired
    SendEmailService sendEmailService;

    @Override
    public User login(LoginForm loginForm) {
        User user = User.builder()
                .email(loginForm.getEmail())
                .password(SHA256Service.getSHA256(loginForm.getPassword()))
                .build();
        Example<User> userExample = Example.of(user, ExampleMatcher.matchingAll());
        List<User> users = userRepoJpa.findAll(userExample);
        return users.size() == 1 ? users.get(0) : null;
    }

    @Override
    public boolean register(RegisterForm registerForm) {
        List<User> oldUsers = userRepoJpa.findByEmail(registerForm.getEmail());
        List<Admin> oldAdmins = adminRepoJpa.findByEmail(registerForm.getEmail());
        if(oldUsers.size() != 0 || oldAdmins.size() != 0) return false;
        String activeCode = getRandomCode();
        boolean b = sendEmailService.sendMailHtml(registerForm.getEmail()
                , "Active account at Bike Share system"
                , activeCode, registerForm);

        if(!b) return false;
        userNotActiveJpa.save(UserNotActive.builder()
                .address(registerForm.getAddress())
                .birthday(registerForm.getBirthday())
                .city(registerForm.getCity())
                .creditCard(registerForm.getCreditCard())
                .district(registerForm.getDistrict())
                .email(registerForm.getEmail())
                .firstname(registerForm.getFirstname())
                .gender(registerForm.getGender())
                .id(0)
                .lastname(registerForm.getLastname())
                .password(SHA256Service.getSHA256(registerForm.getPassword()))
                .phone(registerForm.getPhone())
                .code(activeCode)
                .build());
        return true;
    }

    @Override
    public boolean activeAccount(String code) {

        Example<UserNotActive> userExample = Example.of(UserNotActive.builder().code(code).build(), ExampleMatcher.matchingAll());
        List<UserNotActive> users = userNotActiveJpa.findAll(userExample);
        if(users.size()==1){
            userRepoJpa.save(User.builder()
                    .id(0)
                    .address(users.get(0).getAddress())
                    .phone(users.get(0).getPhone())
                    .password(users.get(0).getPassword())
                    .lastname(users.get(0).getLastname())
                    .gender(users.get(0).getGender())
                    .firstname(users.get(0).getFirstname())
                    .email(users.get(0).getEmail())
                    .district(users.get(0).getDistrict())
                    .creditCard(users.get(0).getCreditCard())
                    .city(users.get(0).getCity())
                    .birthday(users.get(0).getBirthday())
                    .build());
            userNotActiveJpa.delete(users.get(0));
            return true;
        }
        return false;
    }

    public String getRandomCode(){
        StringBuilder rs= new StringBuilder();
        for (int i=0; i< 4; i++){
            rs.append((int) (Math.random() * 10));
        }
        return rs.toString() + new Date().getTime()/1000;
    }
}
