package com.hongdatchy.bikeshare.entities.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

@Data
public class RegisterForm {

    private String email;
    private String password;
    private String rePassword;
    private String phone;
    private String firstname;
    private String lastname;
    private String creditCard;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private Date birthday;
    private String gender;
    private String address;
    private Integer districtId;
    private Integer cityId;
    private Integer wardId;
}
