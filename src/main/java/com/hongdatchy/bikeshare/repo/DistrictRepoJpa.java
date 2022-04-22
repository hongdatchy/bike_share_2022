/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 21/04/2022
 */
package com.hongdatchy.bikeshare.repo;

import com.hongdatchy.bikeshare.entities.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 *
 * @author hongdatchy
 */
@Repository
public interface DistrictRepoJpa extends JpaRepository<District, Integer> {
    List<District> findByCityId(int cityId);
}
