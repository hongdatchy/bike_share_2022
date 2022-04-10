package com.hongdatchy.bikeshare.entities.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "path")
@Data
public class Path  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "contract_id", nullable = false)
    private Integer contractId;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "start_time", nullable = false)
    private Timestamp startTime;

    @Column(name = "end_time", nullable = false)
    private Timestamp endTime;

    @Column(name = "routes", nullable = false, columnDefinition = "LONGTEXT")
    private String routes;

}
