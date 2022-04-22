package com.hongdatchy.bikeshare.entities.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City  implements Comparable<City>{

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Override
    public int compareTo(City o) {
        if(o.id!= null && id!= null){
            return id.compareTo(o.id);
        }
        return 0;
    }
}
