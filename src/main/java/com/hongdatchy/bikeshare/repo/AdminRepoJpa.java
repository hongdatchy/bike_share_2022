package com.hongdatchy.bikeshare.repo;

import com.hongdatchy.bikeshare.entities.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepoJpa extends JpaRepository<Admin, Integer> {
    List<Admin> findByEmail(String phone);
}
