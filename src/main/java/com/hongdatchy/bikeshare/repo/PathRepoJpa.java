package com.hongdatchy.bikeshare.repo;

import com.hongdatchy.bikeshare.entities.model.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PathRepoJpa extends JpaRepository<Path, Integer> {
}
