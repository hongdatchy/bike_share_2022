package com.hongdatchy.bikeshare.controller;

import com.hongdatchy.bikeshare.entities.model.Device;
import com.hongdatchy.bikeshare.entities.response.MyResponse;
import com.hongdatchy.bikeshare.repo.DeviceRepoJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DeviceController {
    @Autowired
    DeviceRepoJpa deviceRepoJpa;

    @GetMapping("api/ad/device")
    ResponseEntity<Object> findAll(){
        List<Device> devices = deviceRepoJpa.findAll();
        return ResponseEntity.ok(MyResponse.success(devices));
    }

    @GetMapping("api/common/device/{id}")
    ResponseEntity<Object> findById(@PathVariable int id){
        Optional<Device> device = deviceRepoJpa.findById(id);
        return device.isPresent() ? ResponseEntity.ok(MyResponse.success(device)) :
                ResponseEntity.ok(MyResponse.fail("is is incorrect"));
    }

    @GetMapping("api/common/{bikeId}")
    ResponseEntity<Object> findByBikeId(@PathVariable int bikeId){
        List<Device> devices = deviceRepoJpa.findByBikeId(bikeId);
        return ResponseEntity.ok(MyResponse.success(devices));
    }

    @DeleteMapping("api/ad/device/{id}")
    ResponseEntity<Object> deleteById(@PathVariable int id){
        if(deviceRepoJpa.findById(id).isPresent()){
            deviceRepoJpa.deleteById(id);
            return ResponseEntity.ok(MyResponse.success(""));
        }else return ResponseEntity.ok(MyResponse.fail(""));
    }

    @PostMapping("api/ad/device")
    ResponseEntity<Object> createAndUpdate(@RequestBody Device device){
        return ResponseEntity.ok(MyResponse.success(deviceRepoJpa.save(device)));
    }
}
