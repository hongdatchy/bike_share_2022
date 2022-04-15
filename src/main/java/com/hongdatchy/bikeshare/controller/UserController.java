package com.hongdatchy.bikeshare.controller;

import com.hongdatchy.bikeshare.entities.model.Contract;
import com.hongdatchy.bikeshare.entities.model.Path;
import com.hongdatchy.bikeshare.entities.model.User;
import com.hongdatchy.bikeshare.entities.request.RentBikeRequest;
import com.hongdatchy.bikeshare.entities.response.MyResponse;
import com.hongdatchy.bikeshare.repo.ContractRepoJpa;
import com.hongdatchy.bikeshare.repo.PathRepoJpa;
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

    @Autowired
    MqttService mqttService;

    @Autowired
    UserRepoJpa userRepoJpa;

    @Autowired
    PathRepoJpa pathRepoJpa;

    @PostMapping("api/us/rent-bike")
    ResponseEntity<Object> rendBike(@RequestBody RentBikeRequest rentBikeRequest, @RequestAttribute Integer userId, @RequestHeader String token){
        // fe call api thue xe --> neu thanh cong ( đang hiện tại là thành công 100%)
        // thì đẩy bản tin mqtt thông báo cho thiết bị là hãy mở khoá
        Contract contract = contractRepoJpa.save(Contract.builder()
                                .bikeId(rentBikeRequest.getBikeId())
                                .createDatetime(new Timestamp(new Date().getTime()))
                                .id(0)
                                .paymentMethod(rentBikeRequest.getPaymentMethod())
                                .userId(userId)
                                .build());
        pathRepoJpa.save(Path.builder()
                .id(0)
                .contractId(contract.getId())
                .distance(0.0)
                .routes("")
                .startTime(new Timestamp(new Date().getTime()))
                .endTime(new Timestamp(new Date().getTime()))
                .build());
        mqttService.publish(rentBikeRequest.getBikeId(), "o");
        return ResponseEntity.ok(MyResponse.success(contract));
    }

    @GetMapping("api/ad/user")
    ResponseEntity<Object> findAll(@RequestHeader String token){
        List<User> users = userRepoJpa.findAll();
        return ResponseEntity.ok(MyResponse.success(users));
    }

    @DeleteMapping("api/ad/user/{id}")
    ResponseEntity<Object> deleteById(@PathVariable int id, @RequestHeader String token){
        if(userRepoJpa.findById(id).isPresent()){
            userRepoJpa.deleteById(id);
            return ResponseEntity.ok(MyResponse.success(""));
        }else return ResponseEntity.ok(MyResponse.fail(""));
    }

    @PostMapping("api/ad/user")
    ResponseEntity<Object> createAndUpdate(@RequestBody User user, @RequestHeader String token){
        return ResponseEntity.ok(MyResponse.success(userRepoJpa.save(user)));
    }
    
}
