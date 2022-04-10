package com.hongdatchy.bikeshare.controller;

import com.hongdatchy.bikeshare.entities.model.Admin;
import com.hongdatchy.bikeshare.entities.model.User;
import com.hongdatchy.bikeshare.entities.request.LoginForm;
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
        System.out.println(loginForm);
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

//    @GetMapping("api/public/user/book")
//    public ResponseEntity<Object> test() {
//        return ResponseEntity.ok(MyResponse.success("test"));
//    }
}
