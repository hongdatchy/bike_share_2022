package com.hongdatchy.bikeshare.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hongdatchy.bikeshare.entities.model.Contract;
import com.hongdatchy.bikeshare.entities.model.Coordinate;
import com.hongdatchy.bikeshare.entities.model.Path;
import com.hongdatchy.bikeshare.entities.response.MyResponse;
import com.hongdatchy.bikeshare.entities.response.PathResponse;
import com.hongdatchy.bikeshare.repo.ContractRepoJpa;
import com.hongdatchy.bikeshare.repo.PathRepoJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PathController {

    @Autowired
    PathRepoJpa pathRepoJpa;

    @Autowired
    ContractRepoJpa contractRepoJpa;


    @GetMapping("api/us/path")
    ResponseEntity<Object> usFindAll(@RequestAttribute Integer userId){
        List<Contract> contracts = contractRepoJpa.findByUserId(userId);
        List<Path> paths = new ArrayList<>();
        for (Contract contract : contracts){
            List<Path> pathByContractId = pathRepoJpa.findByContractId(contract.getId());
            if(!pathByContractId.isEmpty()){
                paths.addAll(pathByContractId);
            }
        }
        return ResponseEntity.ok(MyResponse.success(
                paths.stream().map(path -> PathResponse.builder()
                        .contractId(path.getContractId())
                        .distance(path.getDistance())
                        .id(path.getId())
                        .coordinates(new Gson().fromJson(path.getRoutes(), new TypeToken<List<Coordinate>>(){}.getType()))
                        .build())
        ));
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
