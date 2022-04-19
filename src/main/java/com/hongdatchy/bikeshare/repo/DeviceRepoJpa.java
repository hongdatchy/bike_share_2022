package com.hongdatchy.bikeshare.repo;

import com.hongdatchy.bikeshare.entities.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepoJpa extends JpaRepository<Device, Integer> {

    List<Device> findByBikeId(int bikeId);
}
