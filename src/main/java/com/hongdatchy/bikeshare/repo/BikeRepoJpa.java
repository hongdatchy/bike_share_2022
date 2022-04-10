package com.hongdatchy.bikeshare.repo;

import com.hongdatchy.bikeshare.entities.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepoJpa extends JpaRepository<Bike, Integer> {
//    List<B>
}
