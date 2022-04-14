package com.hongdatchy.bikeshare.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hongdatchy.bikeshare.entities.model.Coordinate;
import com.hongdatchy.bikeshare.entities.model.Path;
import com.hongdatchy.bikeshare.entities.response.MyResponse;
import com.hongdatchy.bikeshare.entities.response.PathResponse;
import com.hongdatchy.bikeshare.repo.PathRepoJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class PathController {

    @Autowired
    PathRepoJpa pathRepoJpa;

    @GetMapping("api/us/path/{id}")
    ResponseEntity<Object> usFindById(@PathVariable int id){
        Optional<Path> path = pathRepoJpa.findById(id);
        if(path.isPresent()){
            return ResponseEntity.ok(MyResponse.success(PathResponse.builder()
                    .contractId(path.get().getContractId())
                    .distance(path.get().getDistance())
                    .endTime(path.get().getEndTime())
                    .id(path.get().getId())
                    .coordinates(new Gson().fromJson(path.get().getRoutes(), new TypeToken<List<Coordinate>>(){}.getType()))
                    .build()));
        }
        return ResponseEntity.ok(MyResponse.fail("id of path is incorrect"));
    }

    @GetMapping("api/ad/path")
    ResponseEntity<Object> findAll(){
        List<Path> paths = pathRepoJpa.findAll();
        return ResponseEntity.ok(MyResponse.success(paths));
    }

    @DeleteMapping("api/ad/path/{id}")
    ResponseEntity<Object> deleteById(@PathVariable int id){
        if(pathRepoJpa.findById(id).isPresent()){
            pathRepoJpa.deleteById(id);
            return ResponseEntity.ok(MyResponse.success(""));
        }else return ResponseEntity.ok(MyResponse.fail(""));
    }

    @PostMapping("api/ad/path")
    ResponseEntity<Object> createAndUpdate(@RequestBody Path path){
        return ResponseEntity.ok(MyResponse.success(pathRepoJpa.save(path)));
    }

}
