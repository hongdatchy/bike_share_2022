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

    @Column(name = "routes", nullable = false, columnDefinition = "LONGTEXT")
    private String routes;

}
