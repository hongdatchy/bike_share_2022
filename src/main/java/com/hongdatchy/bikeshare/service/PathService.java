/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 13/04/2022
 */
package com.hongdatchy.bikeshare.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hongdatchy.bikeshare.entities.model.Path;

import java.sql.Timestamp;

/**
 *
 *
 * @author hongdatchy
 */
public interface PathService {
    Path updatePathFormGPS(int bikeId, String latitude, String longitude, Timestamp timestamp) throws JsonProcessingException;
}
