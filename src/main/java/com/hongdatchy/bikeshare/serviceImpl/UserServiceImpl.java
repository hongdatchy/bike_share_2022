package com.hongdatchy.bikeshare.serviceImpl;

import com.hongdatchy.bikeshare.entities.model.Admin;
import com.hongdatchy.bikeshare.entities.model.User;
import com.hongdatchy.bikeshare.entities.request.LoginForm;
import com.hongdatchy.bikeshare.repo.AdminRepoJpa;
import com.hongdatchy.bikeshare.repo.UserRepoJpa;
import com.hongdatchy.bikeshare.security.SHA256Service;
import com.hongdatchy.bikeshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepoJpa userRepoJpa;



    @Override
    public User login(LoginForm loginForm) {
        User user = User.builder()
                .email(loginForm.getUsername())
                .password(SHA256Service.getSHA256(loginForm.getPassword()))
                .build();
        Example<User> userExample = Example.of(user, ExampleMatcher.matchingAll());
        List<User> users = userRepoJpa.findAll(userExample);
        return users.size() == 1 ? users.get(0) : null;

    }
}
