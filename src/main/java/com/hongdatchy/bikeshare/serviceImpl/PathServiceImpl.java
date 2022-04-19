/**
 * Copyright(C) 2022 SanLab Hust
 * class.java, 13/04/2022
 */
package com.hongdatchy.bikeshare.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hongdatchy.bikeshare.entities.model.Contract;
import com.hongdatchy.bikeshare.entities.model.Coordinate;
import com.hongdatchy.bikeshare.entities.model.Path;
import com.hongdatchy.bikeshare.repo.ContractRepoJpa;
import com.hongdatchy.bikeshare.repo.PathRepoJpa;
import com.hongdatchy.bikeshare.service.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author hongdatchy
 */
@Service
public class PathServiceImpl implements PathService {

    @Autowired
    PathRepoJpa pathRepoJpa;

    @Autowired
    ContractRepoJpa contractRepoJpa;

    /**
     *
     * 1 get all contract with bikeId
     *
     * 2 update path that have contractId equal last contract in list above
     */
    @Override
    public Path updatePathFormGPS(Contract contract, int bikeId, double latitude, double longitude) {

        List<Path> paths = pathRepoJpa.findByContractId(contract.getId());
        if (paths.size()==0) {
            List<Coordinate> coordinates = new ArrayList<>();
            coordinates.add(new Coordinate(latitude, longitude));
            return pathRepoJpa.save(Path.builder()
                    .id(0)
                    .contractId(contract.getId())
                    .distance(0.0)
                    .routes(new Gson().toJson(coordinates))
                    .build());
        } else if(paths.size()==1){
            List<Coordinate> coordinates = new Gson().fromJson(paths.get(0).getRoutes(), new TypeToken<List<Coordinate>>(){}.getType());
            double latPre = coordinates.get(coordinates.size()-1).getLatitude();
            double longPre = coordinates.get(coordinates.size()-1).getLongitude();
            double addDistance = calculateDistance(latPre , longPre , latitude , longitude);
            paths.get(0).setDistance(paths.get(0).getDistance() + addDistance);
            coordinates.add(new Coordinate(latitude, longitude));
            paths.get(0).setRoutes(new Gson().toJson(coordinates));
            return pathRepoJpa.save(paths.get(0));
        }else {
            return null;
        }

    }

    /**
     * calculate distance between two coordinate
     *
     * @param lat1 lat1
     * @param lng1 lng1
     * @param lat2 lat2
     * @param lng2 lng2
     * @return number km between two coordinate
     */
    public double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return earthRadius * c;
    }
}
