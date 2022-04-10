package com.hongdatchy.bikeshare.entities.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "station")
@Data
public class Station  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slot_quantity", nullable = false)
    private Integer slotQuantity;

    @Column(name = "current_number_car", nullable = false)
    private Integer currentNumberCar;

    @Column(name = "location", nullable = false)
    private String location;

}
