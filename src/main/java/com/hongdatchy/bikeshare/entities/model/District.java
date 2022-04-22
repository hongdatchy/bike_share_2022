package com.hongdatchy.bikeshare.entities.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "district")
@Entity
@Data

public class District implements Comparable<District>{

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "city_id", nullable = false)
    private Integer cityId;

    @Override
    public int compareTo(District o) {
        if(o.id!= null && id!= null){
            return id.compareTo(o.id);
        }
        return 0;
    }


}
