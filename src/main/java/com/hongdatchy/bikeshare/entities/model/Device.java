package com.hongdatchy.bikeshare.entities.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "device")
public class Device  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "bike_id", nullable = false)
    private Integer bikeId;

    @Column(name = "status_lock")
    private Double statusLock;

    @Column(name = "longitude", nullable = false)
    private String longitude;

    @Column(name = "latitude", nullable = false)
    private String latitude;

}
