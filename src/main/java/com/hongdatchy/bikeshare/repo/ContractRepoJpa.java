package com.hongdatchy.bikeshare.repo;

import com.hongdatchy.bikeshare.entities.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepoJpa extends JpaRepository<Contract, Integer> {
    List<Contract> findByBikeId(int bikeId);

    List<Contract> findByUserId(int userId);
}
