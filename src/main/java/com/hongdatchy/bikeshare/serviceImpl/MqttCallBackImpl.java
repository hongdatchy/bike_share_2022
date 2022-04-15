package com.hongdatchy.bikeshare.serviceImpl;

import com.hongdatchy.bikeshare.service.PathService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MqttCallBackImpl implements MqttCallback {

    @Autowired
    PathService pathService;

    public void connectionLost(Throwable cause) {
        // After the connection is lost, it usually reconnects here
        System.out.println("disconnect，you can reconnect");

    }

    /**
     *
     * @param topic topic
     * @param message mqtt message (message from device to server)
     *      update path : p,bikeId,hhmmss,lat,lon
     *      open device : o (open)
     *      open device : c (close)
     */
    public void messageArrived(String topic, MqttMessage message)  {
        // The messages obtained after subscribe will be executed here
        System.out.println("Received message topic:" + topic);
//        System.out.println("Received message Qos:" + message.getQos());
//        System.out.println("Received message content:" + new String(message.getPayload()));

        String[] mess = new String(message.getPayload()).split(",");
        try {
            if(mess[0].equals("p")){
                Date date = new SimpleDateFormat("ddMMyyyyhhmmss")
                        .parse(new SimpleDateFormat("ddMMyyyy").format(new Date()) + mess[2]);
                pathService.updatePathFormGPS(Integer.parseInt(mess[1]), mess[3], mess[4], new Timestamp(date.getTime()));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }
}
