package com.hongdatchy.bikeshare.entities.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+7")
    @Column(name = "start_time", nullable = false)
    private Timestamp startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+7")
    @Column(name = "end_time")
    private Timestamp endTime;

}
