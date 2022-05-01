package com.hongdatchy.bikeshare.controller;

import com.hongdatchy.bikeshare.entities.model.Contract;
import com.hongdatchy.bikeshare.entities.model.Device;
import com.hongdatchy.bikeshare.entities.model.Path;
import com.hongdatchy.bikeshare.entities.model.User;
import com.hongdatchy.bikeshare.entities.request.RentBikeRequest;
import com.hongdatchy.bikeshare.entities.response.MyResponse;
import com.hongdatchy.bikeshare.repo.ContractRepoJpa;
import com.hongdatchy.bikeshare.repo.DeviceRepoJpa;
import com.hongdatchy.bikeshare.repo.PathRepoJpa;
import com.hongdatchy.bikeshare.repo.UserRepoJpa;
import com.hongdatchy.bikeshare.service.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    @Autowired
    DeviceRepoJpa deviceRepoJpa;

    @Autowired
    private SimpMessagingTemplate template;

    @PostMapping("api/us/rent-bike")
    ResponseEntity<Object> rendBike(@RequestBody RentBikeRequest rentBikeRequest, @RequestAttribute Integer userId){
        // kiểm tra xem status device có đang đóng hay không
        List<Device> devices = deviceRepoJpa.findByBikeId(rentBikeRequest.getBikeId());
        if(devices.size() == 1 && !devices.get(0).getStatusLock()){
            // tạo contract mới set time start là thời điểm hiện tại
            // time end là null, sẽ được cập nhật từ mqtt sau khi tạo contract
            Contract contract = contractRepoJpa.save(Contract.builder()
                    .bikeId(rentBikeRequest.getBikeId())
                    .id(0)
                    .paymentMethod(rentBikeRequest.getPaymentMethod())
                    .startTime(new Timestamp(new Date().getTime()))
                    .userId(userId)
                    .build());

            // đẩy bản tin mqtt thông báo cho thiết bị là hãy mở khoá
            mqttService.publish(rentBikeRequest.getBikeId(), "op");

            // thông báo cho android là đã mở khoá
            template.convertAndSend("/topic/greetings/" + rentBikeRequest.getBikeId(), "op");

            // thay đổi status của device
            devices.get(0).setStatusLock(true);
            deviceRepoJpa.save(devices.get(0));
            return ResponseEntity.ok(MyResponse.success(contract));
        }
        return ResponseEntity.ok(MyResponse.fail("Bike has not device or bike is being rented"));

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
