package com.hongdatchy.bikeshare.controller;

import com.hongdatchy.bikeshare.entities.model.Path;
import com.hongdatchy.bikeshare.entities.response.MyResponse;
import com.hongdatchy.bikeshare.repo.PathRepoJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PathController {

    @Autowired
    PathRepoJpa pathRepoJpa;

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
