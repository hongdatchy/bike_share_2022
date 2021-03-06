package com.hongdatchy.bikeshare.serviceImpl;

import com.hongdatchy.bikeshare.service.MqttService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MqttServiceImpl implements MqttService {
    String broker = "tcp://155.248.164.224:1883";
    String clientId = MqttAsyncClient.generateClientId();
    MqttClient client;

    @Autowired
    MqttCallback mqttCallback;

    @PostConstruct
    public void innit(){
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName("sanslab");
            connOpts.setPassword("1".toCharArray());
            // retain session
            connOpts.setCleanSession(true);
            client.connect(connOpts);
        }catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void publish(int bikeId, String mess) {
        String pubTopic = "downstream/"+ bikeId;
        int qos = 2; // --> best
//        QoS = 0 (at-most-once)
//        QoS = 1 (at-least-one)
//        QoS = 2 (Exactly-once)
        MqttMessage message = new MqttMessage(mess.getBytes());
        message.setQos(qos);
        try {
            client.publish(pubTopic, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribe(int bikeId) {
        String subTopic = "upstream/"+ bikeId;

        try {
            // set callback
            client.setCallback(mqttCallback);
            // Subscribe
            client.subscribe(subTopic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribeAll() {
        String subTopic = "upstream/#";

        try {
            // set callback
            client.setCallback(mqttCallback);
            // Subscribe
            client.subscribe(subTopic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
