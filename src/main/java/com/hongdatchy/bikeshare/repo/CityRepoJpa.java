/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 21/04/2022
 */
package com.hongdatchy.bikeshare.repo;

import com.hongdatchy.bikeshare.entities.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author hongdatchy
 */
@Repository
public interface CityRepoJpa extends JpaRepository<City, Integer > {
}
