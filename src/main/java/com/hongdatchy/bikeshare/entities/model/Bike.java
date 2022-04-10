package com.hongdatchy.bikeshare.entities.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "bike")
@Data
public class Bike  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "station_id", nullable = false)
    private Integer stationId;

    @Column(name = "frame_number", nullable = false)
    private String frameNumber;

    @Column(name = "product_year", nullable = false)
    private String productYear;

}
