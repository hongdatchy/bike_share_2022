package com.hongdatchy.bikeshare.service;

public interface MqttService {
    void publish(int bikeId, String mess);
    void subscribe(int bikeId);
    void subscribeAll();
}
