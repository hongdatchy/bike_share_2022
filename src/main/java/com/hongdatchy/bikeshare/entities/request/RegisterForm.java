package com.hongdatchy.bikeshare.entities.request;

import lombok.Data;

import java.sql.Date;

@Data
public class RegisterForm {

    private String email;
    private String password;
    private String rePassword;
    private String phone;
    private int id;
    private String firstname;
    private String lastname;
    private String creditCard;
    private Date birthday;
    private String gender;
    private String address;
    private String district;
    private String city;
}
