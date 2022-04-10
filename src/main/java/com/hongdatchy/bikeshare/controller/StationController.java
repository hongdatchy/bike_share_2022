package com.hongdatchy.bikeshare.controller;

import com.hongdatchy.bikeshare.entities.model.Station;
import com.hongdatchy.bikeshare.entities.response.MyResponse;
import com.hongdatchy.bikeshare.repo.StationRepoJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StationController {
    @Autowired
    StationRepoJpa stationRepoJpa;

    @GetMapping("api/ad/station")
    ResponseEntity<Object> findAll(){
        List<Station> stations = stationRepoJpa.findAll();
        return ResponseEntity.ok(MyResponse.success(stations));
    }

    @DeleteMapping("api/ad/station/{id}")
    ResponseEntity<Object> deleteById(@PathVariable int id){
        if(stationRepoJpa.findById(id).isPresent()){
            stationRepoJpa.deleteById(id);
            return ResponseEntity.ok(MyResponse.success(""));
        }else return ResponseEntity.ok(MyResponse.fail(""));
    }

    @PostMapping("api/ad/station")
    ResponseEntity<Object> createAndUpdate(@RequestBody Station station){
        return ResponseEntity.ok(MyResponse.success(stationRepoJpa.save(station)));
    }



}
