package com.hongdatchy.bikeshare.entities.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "contract")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contract  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "bike_id", nullable = false)
    private Integer bikeId;

    @Column(name = "create_datetime", nullable = false)
    private Timestamp createDatetime;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

}
