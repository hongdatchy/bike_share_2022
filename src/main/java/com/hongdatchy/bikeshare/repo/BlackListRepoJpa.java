package com.hongdatchy.bikeshare.repo;

import com.hongdatchy.bikeshare.entities.model.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlackListRepoJpa extends JpaRepository<BlackList, Integer> {
    List<BlackList> findByToken(String token);
}
