package com.hongdatchy.bikeshare.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hongdatchy.bikeshare.entities.model.Bike;
import com.hongdatchy.bikeshare.entities.model.Contract;
import com.hongdatchy.bikeshare.entities.model.Device;
import com.hongdatchy.bikeshare.repo.ContractRepoJpa;
import com.hongdatchy.bikeshare.repo.DeviceRepoJpa;
import com.hongdatchy.bikeshare.service.PathService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Component
public class MqttCallBackImpl implements MqttCallback {

    @Autowired
    PathService pathService;

    @Autowired
    ContractRepoJpa contractRepoJpa;

    @Autowired
    DeviceRepoJpa deviceRepoJpa;

    public void connectionLost(Throwable cause) {
        // After the connection is lost, it usually reconnects here
        System.out.println("disconnect，you can reconnect");
    }

    /**
     *
     * @param topic topic
     * @param message mqtt message (message from device to server)
     *      update path and lat, long of device : p,HHmmss,lat,lon (position)
     *      open device : cl (close)
     */
    public void messageArrived(String topic, MqttMessage message)  {
        // The messages obtained after subscribe will be executed here
        System.out.println("Received message topic:" + topic);
//        System.out.println("Received message Qos:" + message.getQos());
//        System.out.println("Received message content:" + new String(message.getPayload()));

        String[] mess = new String(message.getPayload()).split(",");
        System.out.println(Arrays.toString(mess));
        try {
            String[] topicSplit = topic.split("/");
            int bikeId = Integer.parseInt(topicSplit[topicSplit.length-1]);
            List<Contract> contracts = contractRepoJpa.findByBikeId(bikeId);
            if(mess[0].equals("p")) {
                List<Device> devices = deviceRepoJpa.findByBikeId(bikeId);
                double latitude = Double.parseDouble(mess[2]);
                double longitude = Double.parseDouble(mess[3]);

                if(devices.get(0).getStatusLock()){ // true --> xa đang được thuê
                    if(contracts.size() > 0){
                        // update time end to contract
                        Contract contract = contracts.get(contracts.size()-1);
                        String timeGtm0 = mess[1];
                        SimpleDateFormat f = new SimpleDateFormat("ddMMyyyyHHmmss");
                        f.setTimeZone(TimeZone.getTimeZone("GMT+0"));
                        // vì mqtt đang gửi lên gmt+0
                        Date date = f.parse(new SimpleDateFormat("ddMMyyyy").format(new Date())+ timeGtm0);
                        contract.setEndTime(new Timestamp(date.getTime()));
                        contractRepoJpa.save(contract);

                        // update path
                        pathService.updatePathFormGPS(contract, bikeId, latitude, longitude);
                    }
                }
                // luôn cập nhật cho device
                devices.get(0).setLatitude(latitude);
                devices.get(0).setLongitude(longitude);
                deviceRepoJpa.save(devices.get(0));


            } else if (mess[0].equals("cl")) {
                List<Device> devices = deviceRepoJpa.findByBikeId(bikeId);
                if(devices.size() == 1){
                    devices.get(0).setStatusLock(false);
                    deviceRepoJpa.save(devices.get(0));
                }
            }
        } catch (ParseException | JsonProcessingException e){
            e.printStackTrace();
        }
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }
}
