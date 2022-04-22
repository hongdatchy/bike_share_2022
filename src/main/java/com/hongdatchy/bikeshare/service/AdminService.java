package com.hongdatchy.bikeshare.service;

import com.hongdatchy.bikeshare.entities.model.Admin;
import com.hongdatchy.bikeshare.entities.request.LoginForm;

public interface AdminService {

    Admin login(LoginForm loginForm);


}
