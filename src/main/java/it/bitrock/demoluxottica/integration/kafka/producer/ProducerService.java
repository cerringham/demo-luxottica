package it.bitrock.demoluxottica.integration.kafka.producer;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService<V> {

    private static final Logger log = LoggerFactory.getLogger(ProducerService.class);

    private final KafkaTemplate<String, V> kafkaTemplate;


    @Autowired
    public ProducerService(KafkaTemplate<String, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String key, V value, String topic) throws ExecutionException, InterruptedException {
        kafkaTemplate.send(topic, key, value).get();
        log.info("Created message inside \ntopic: {},\nkey: {}, \nvalue: {}", topic, key, value);
    }
}
