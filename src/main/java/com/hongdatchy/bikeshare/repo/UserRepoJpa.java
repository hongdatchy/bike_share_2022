package com.hongdatchy.bikeshare.repo;

import com.hongdatchy.bikeshare.entities.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepoJpa extends JpaRepository<User, Integer> {
    List<User> findByEmail(String phone);
}
