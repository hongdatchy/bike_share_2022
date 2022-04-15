package com.hongdatchy.bikeshare.controller;

import com.hongdatchy.bikeshare.entities.model.Device;
import com.hongdatchy.bikeshare.entities.response.MyResponse;
import com.hongdatchy.bikeshare.repo.DeviceRepoJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeviceController {
    @Autowired
    DeviceRepoJpa deviceRepoJpa;

    @GetMapping("api/ad/device")
    ResponseEntity<Object> findAll(@RequestHeader String token){
        List<Device> devices = deviceRepoJpa.findAll();
        return ResponseEntity.ok(MyResponse.success(devices));
    }

    @DeleteMapping("api/ad/device/{id}")
    ResponseEntity<Object> deleteById(@PathVariable int id, @RequestHeader String token){
        if(deviceRepoJpa.findById(id).isPresent()){
            deviceRepoJpa.deleteById(id);
            return ResponseEntity.ok(MyResponse.success(""));
        }else return ResponseEntity.ok(MyResponse.fail(""));
    }

    @PostMapping("api/ad/device")
    ResponseEntity<Object> createAndUpdate(@RequestBody Device device, @RequestHeader String token){
        return ResponseEntity.ok(MyResponse.success(deviceRepoJpa.save(device)));
    }
}
