/**
 * Copyright(C) 2022 SanLab VSet Hust
 *
 * BikeShareApplication.java, April 2022 hongdatchy
 */
package com.hongdatchy.bikeshare;

import com.hongdatchy.bikeshare.service.MqttService;
import com.hongdatchy.bikeshare.service.SendEmailService;
import com.hongdatchy.bikeshare.utils.CreateDataAddressOfVietNam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.util.TimeZone;

@SpringBootApplication
@EnableSwagger2
public class BikeShareApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BikeShareApplication.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/**")).build();
    }
    @Autowired
    com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @Autowired
    MqttService  mqttService;

    @Autowired
    SendEmailService sendEmailService;

    @Override
    public void run(String... args) throws IOException {
//        set timezone cho backend
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
//        set timezone cho controller
        objectMapper.setTimeZone(TimeZone.getDefault());

        mqttService.subscribeAll();


    }

}
