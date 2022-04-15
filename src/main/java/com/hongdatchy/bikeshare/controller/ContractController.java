package com.hongdatchy.bikeshare.controller;

import com.hongdatchy.bikeshare.entities.model.Contract;
import com.hongdatchy.bikeshare.entities.response.MyResponse;
import com.hongdatchy.bikeshare.repo.ContractRepoJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContractController {

    @Autowired
    ContractRepoJpa contractRepoJpa;

    @PostMapping("api/us/contract")
    ResponseEntity<Object> usFindAll(@RequestAttribute Integer userId, @RequestHeader String token){
        List<Contract> contracts = contractRepoJpa.findByUserId(userId);
        return ResponseEntity.ok(MyResponse.success(contracts));
    }

    @GetMapping("api/ad/contract")
    ResponseEntity<Object> findAll(@RequestHeader String token){
        List<Contract> contracts = contractRepoJpa.findAll();
        return ResponseEntity.ok(MyResponse.success(contracts));
    }

    @DeleteMapping("api/ad/contract/{id}")
    ResponseEntity<Object> deleteById(@PathVariable int id, @RequestHeader String token){
        if(contractRepoJpa.findById(id).isPresent()){
            contractRepoJpa.deleteById(id);
            return ResponseEntity.ok(MyResponse.success(""));
        }else return ResponseEntity.ok(MyResponse.fail(""));
    }

    @PostMapping("api/ad/contract")
    ResponseEntity<Object> createAndUpdate(@RequestBody Contract contract, @RequestHeader String token){
        return ResponseEntity.ok(MyResponse.success(contractRepoJpa.save(contract)));
    }
}
