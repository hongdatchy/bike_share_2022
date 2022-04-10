package com.hongdatchy.bikeshare.controller;

import com.hongdatchy.bikeshare.entities.model.Contract;
import com.hongdatchy.bikeshare.entities.model.User;
import com.hongdatchy.bikeshare.entities.request.RentBikeRequest;
import com.hongdatchy.bikeshare.entities.response.MyResponse;
import com.hongdatchy.bikeshare.repo.ContractRepoJpa;
import com.hongdatchy.bikeshare.repo.UserRepoJpa;
import com.hongdatchy.bikeshare.service.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    ContractRepoJpa contractRepoJpa;

//    @Autowired
//    MqttService mqttService;

    @Autowired
    UserRepoJpa userRepoJpa;

    @PostMapping("api/us/rent-bike")
    ResponseEntity<Object> rendBike(@RequestBody RentBikeRequest rentBikeRequest, @RequestAttribute Integer userId){
        Contract contract  = Contract.builder()
                .bikeId(rentBikeRequest.getBikeId())
                .createDatetime(new Timestamp(new Date().getTime()))
                .id(0)
                .paymentMethod(rentBikeRequest.getPaymentMethod())
                .userId(userId)
                .build();
//        mqttService.publish(rentBikeRequest.getBikeId(), "mo khoa");
        return ResponseEntity.ok(MyResponse.success(contractRepoJpa.save(contract)));
    }

    @GetMapping("api/ad/user")
    ResponseEntity<Object> findAll(){
        List<User> users = userRepoJpa.findAll();
        return ResponseEntity.ok(MyResponse.success(users));
    }

    @DeleteMapping("api/ad/user/{id}")
    ResponseEntity<Object> deleteById(@PathVariable int id){
        if(userRepoJpa.findById(id).isPresent()){
            userRepoJpa.deleteById(id);
            return ResponseEntity.ok(MyResponse.success(""));
        }else return ResponseEntity.ok(MyResponse.fail(""));
    }

    @PostMapping("api/ad/user")
    ResponseEntity<Object> createAndUpdate(@RequestBody User user){
        return ResponseEntity.ok(MyResponse.success(userRepoJpa.save(user)));
    }
    
}
