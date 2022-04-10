package com.hongdatchy.bikeshare.repo;

import com.hongdatchy.bikeshare.entities.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepoJpa extends JpaRepository<Contract, Integer> {
}
