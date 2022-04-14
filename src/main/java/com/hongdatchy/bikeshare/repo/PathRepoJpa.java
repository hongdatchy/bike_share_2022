package com.hongdatchy.bikeshare.repo;

import com.hongdatchy.bikeshare.entities.model.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PathRepoJpa extends JpaRepository<Path, Integer> {
    List<Path> findByContractId(int id);
}
