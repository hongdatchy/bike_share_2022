package com.hongdatchy.bikeshare.controller;

import com.hongdatchy.bikeshare.entities.model.Bike;
import com.hongdatchy.bikeshare.entities.response.MyResponse;
import com.hongdatchy.bikeshare.repo.BikeRepoJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BikeController {

    @Autowired
    BikeRepoJpa bikeRepoJpa;

    @GetMapping("api/common/bike/{id}")
    ResponseEntity<Object> findBikeById(@PathVariable int id){
        Optional<Bike> bike = bikeRepoJpa.findById(id);
        return ResponseEntity.ok(bike.isPresent() ?
                MyResponse.success(bike) : MyResponse.fail("id not found"));
    }

    @GetMapping("api/ad/bike")
    ResponseEntity<Object> findAll(){
        List<Bike> bikes = bikeRepoJpa.findAll();
        return ResponseEntity.ok(MyResponse.success(bikes));
    }

    @DeleteMapping("api/ad/bike/{id}")
    ResponseEntity<Object> deleteById(@PathVariable int id){
        if(bikeRepoJpa.findById(id).isPresent()){
            bikeRepoJpa.deleteById(id);
            return ResponseEntity.ok(MyResponse.success(""));
        }else return ResponseEntity.ok(MyResponse.fail(""));
    }

    @PostMapping("api/ad/bike")
    ResponseEntity<Object> createAndUpdate(@RequestBody Bike bike){
        return ResponseEntity.ok(MyResponse.success(bikeRepoJpa.save(bike)));
    }
}
