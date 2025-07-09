package com.rendezvous.rendezvous.Kafka;



import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaStringConsumer {

    @KafkaListener(
            topics = "simple-topic",
            groupId = "simple-group",
            containerFactory = "stringKafkaListenerContainerFactory"
    )
    public void consumeStringMessage(String message) {
        System.out.println("📩 Message simple reçu de Kafka:");
        System.out.println("Contenu: " + message);
    }
}
