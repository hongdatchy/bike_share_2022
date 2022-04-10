package com.hongdatchy.bikeshare.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentBikeRequest {
    Integer bikeId;
    String paymentMethod;
}
