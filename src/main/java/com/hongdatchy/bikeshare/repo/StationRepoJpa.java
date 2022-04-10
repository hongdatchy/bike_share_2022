package com.hongdatchy.bikeshare.repo;

import com.hongdatchy.bikeshare.entities.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepoJpa extends JpaRepository<Station, Integer> {
}
