package com.pm.analyticservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import com.pm.patientservice.event.PatientEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient",groupId = "analytic-service")
    public  void  consumerEvent(byte[]event){
        try {
            PatientEvent patientEvent=PatientEvent.parseFrom(event);
            // ... perform any business related to analytic here

            log.info("Received Patient Event : [PatientId={}, PatientName={}," + "PatientEmail={} ]",patientEvent.getPatientId(),patientEvent.getName(),patientEvent.getEmail());
        } catch (InvalidProtocolBufferException e) {
           log.info("Error deserializing event {}",e.getMessage());
        }
    }
}
