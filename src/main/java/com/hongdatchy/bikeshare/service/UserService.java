package com.hongdatchy.bikeshare.service;

import com.hongdatchy.bikeshare.entities.model.User;
import com.hongdatchy.bikeshare.entities.request.LoginForm;
import com.hongdatchy.bikeshare.entities.request.RegisterForm;

import javax.mail.MessagingException;

public interface UserService {
    User login(LoginForm loginForm);

    boolean register(RegisterForm registerForm) throws MessagingException;
    boolean activeAccount(String code);
}
