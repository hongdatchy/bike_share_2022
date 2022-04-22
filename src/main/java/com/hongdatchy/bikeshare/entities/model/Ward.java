package com.hongdatchy.bikeshare.entities.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ward")
@Data
public class Ward implements Comparable<Ward> {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "district_id", nullable = false)
    private Integer districtId;

    @Override
    public int compareTo(Ward o) {
        if(o.id!= null && id!= null){
            return id.compareTo(o.id);
        }
        return 0;
    }
}
