package com.patien.mspatien.Kafka;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaStringProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaStringProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSimpleMessage(String message) {
        System.out.println("📤 Envoi message simple: " + message);
        kafkaTemplate.send("simple-topic", message);
    }
}
