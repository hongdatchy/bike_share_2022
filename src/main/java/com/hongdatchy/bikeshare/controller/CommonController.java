package com.hongdatchy.bikeshare.controller;

import com.hongdatchy.bikeshare.entities.model.Admin;
import com.hongdatchy.bikeshare.entities.model.BlackList;
import com.hongdatchy.bikeshare.entities.model.User;
import com.hongdatchy.bikeshare.entities.request.LoginForm;
import com.hongdatchy.bikeshare.entities.request.RegisterForm;
import com.hongdatchy.bikeshare.entities.response.LoginResponse;
import com.hongdatchy.bikeshare.entities.response.MyResponse;
import com.hongdatchy.bikeshare.repo.*;
import com.hongdatchy.bikeshare.security.JWTService;
import com.hongdatchy.bikeshare.service.AdminService;
import com.hongdatchy.bikeshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
public class CommonController {
    @Autowired
    JWTService jwtService;

    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    @Autowired
    BlackListRepoJpa blackListRepoJpa;

    @Autowired
    UserRepoJpa userRepoJpa;

    @Autowired
    AdminRepoJpa adminRepoJpa;

    @Autowired
    DistrictRepoJpa districtRepoJpa;

    @Autowired
    CityRepoJpa cityRepoJpa;

    @Autowired
    WardRepoJpa wardRepoJpa;

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

    @GetMapping("api/common/active/{code}")
    public ResponseEntity<Object> active(@PathVariable String code) {
        return userService.activeAccount(code) ? ResponseEntity.ok(MyResponse.success("active success")) :
                ResponseEntity.ok(MyResponse.fail("active fail"));
    }

    @GetMapping("api/common/logout/{token}")
    public ResponseEntity<Object> logout(@PathVariable String token) {
        String email = jwtService.decode(token);
        if(userRepoJpa.findByEmail(email).size()==0){
            if(adminRepoJpa.findByEmail(email).size()==0){
                return ResponseEntity.ok(MyResponse.fail("token is incorrect"));
            }
        }
        if(blackListRepoJpa.findByToken(token).size()==0){
            blackListRepoJpa.save(new BlackList(0, token));
        }
        return ResponseEntity.ok(MyResponse.success("logout success"));
    }

    @GetMapping("api/common/city")
    public ResponseEntity<Object> findAllCity() {
        return ResponseEntity.ok(MyResponse.success(cityRepoJpa.findAll()));
    }

    @GetMapping("api/common/district/{cityId}")
    public ResponseEntity<Object> findDistrictByCityId(@PathVariable int cityId) {
        return ResponseEntity.ok(MyResponse.success(districtRepoJpa.findByCityId(cityId)));
    }

    @GetMapping("api/common/ward/{districtId}")
    public ResponseEntity<Object> findWardByDistrictId(@PathVariable int districtId) {
        return ResponseEntity.ok(MyResponse.success(wardRepoJpa.findByDistrictId(districtId)));
    }
}
