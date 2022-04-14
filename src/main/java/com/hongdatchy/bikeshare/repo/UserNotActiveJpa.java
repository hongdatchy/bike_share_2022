/**
 * Copyright(C) 2022 hongdatchy Company
 * UserNotActiveJpa.java, 12/04/2022
 */
package com.hongdatchy.bikeshare.repo;

import com.hongdatchy.bikeshare.entities.model.UserNotActive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author hongdatchy
 */
@Repository
public interface UserNotActiveJpa extends JpaRepository<UserNotActive, Integer> {
}
