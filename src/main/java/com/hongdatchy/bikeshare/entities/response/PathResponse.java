/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 14/04/2022
 */
package com.hongdatchy.bikeshare.entities.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hongdatchy.bikeshare.entities.model.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 *
 * @author hongdatchy
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PathResponse {

    private Integer id;

    private Integer contractId;

    private Double distance;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+7")
    private Timestamp startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+7")
    private Timestamp endTime;

    private List<Coordinate> coordinates;
}
