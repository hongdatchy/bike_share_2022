package com.hongdatchy.bikeshare.service;


import com.hongdatchy.bikeshare.entities.request.RegisterForm;

public interface SendEmailService {
    boolean sendMail(String userMail, String header, String content);
    boolean sendMailHtml(String userMail, String header, String activeCode, RegisterForm registerForm) ;
}
