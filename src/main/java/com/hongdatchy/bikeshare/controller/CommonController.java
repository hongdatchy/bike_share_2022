package com.hongdatchy.bikeshare.controller;

import com.hongdatchy.bikeshare.entities.model.Admin;
import com.hongdatchy.bikeshare.entities.model.User;
import com.hongdatchy.bikeshare.entities.request.LoginForm;
import com.hongdatchy.bikeshare.entities.request.RegisterForm;
import com.hongdatchy.bikeshare.entities.response.LoginResponse;
import com.hongdatchy.bikeshare.entities.response.MyResponse;
import com.hongdatchy.bikeshare.security.JWTService;
import com.hongdatchy.bikeshare.service.AdminService;
import com.hongdatchy.bikeshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class CommonController {
    @Autowired
    JWTService jwtService;

    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    @PostMapping("api/common/login")
    public ResponseEntity<Object> login(@RequestBody LoginForm loginForm){
        User user = userService.login(loginForm);
        if(user != null){
            return ResponseEntity.ok(
                    MyResponse.success(new LoginResponse("user", jwtService.getToken(user.getEmail())))
            );
        }else {
            Admin admin = adminService.login(loginForm);
            return ResponseEntity.ok(
                    admin != null ?
                        MyResponse.success(new LoginResponse("admin", jwtService.getToken(admin.getEmail())))
                        : MyResponse.fail("username or password is incorrect")
            );
        }
    }

    @PostMapping("api/common/register")
    public ResponseEntity<Object> register(@RequestBody RegisterForm registerForm) throws MessagingException {
        if(!registerForm.getPassword().equals(registerForm.getRePassword())){
            return ResponseEntity.ok(MyResponse.fail("password and rePassword is not equal"));
        }
        boolean isSuccess = userService.register(registerForm);
        return isSuccess ?
                ResponseEntity.ok(MyResponse.success("please check your email")):
                ResponseEntity.ok(MyResponse.fail("email was already used or invalidate"));
    }

    @PostMapping("api/common/active")
    public ResponseEntity<Object> register(@RequestBody String code) {
        return userService.activeAccount(code) ? ResponseEntity.ok(MyResponse.success("active success")) :
                ResponseEntity.ok(MyResponse.fail("active fail"));
    }
}
