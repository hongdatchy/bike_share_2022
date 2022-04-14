package com.hongdatchy.bikeshare.entities.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "path")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Path  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "contract_id", nullable = false)
    private Integer contractId;

    @Column(name = "distance")
    private Double distance;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+7")
    @Column(name = "start_time", nullable = false)
    private Timestamp startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+7")
    @Column(name = "end_time", nullable = false)
    private Timestamp endTime;

    @Column(name = "routes", nullable = false, columnDefinition = "LONGTEXT")
    private String routes;

}
