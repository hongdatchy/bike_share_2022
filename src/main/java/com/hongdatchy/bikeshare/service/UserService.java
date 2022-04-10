package com.hongdatchy.bikeshare.service;

import com.hongdatchy.bikeshare.entities.model.User;
import com.hongdatchy.bikeshare.entities.request.LoginForm;

public interface UserService {
    User login(LoginForm loginForm);
}
