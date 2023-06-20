package it.bitrock.demoluxottica.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaConfiguration {
    public static final String ADDED_TOPIC = "patient-added";
    public static final String REMOVED_TOPIC = "patient-removed";
    public static final String UPDATED_TOPIC = "patient-updated";

    @Bean
    public NewTopic patientAddedTopic() {
        return TopicBuilder.name(ADDED_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic patientRemovedTopic() {
        return TopicBuilder.name(REMOVED_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic patientUpdatedTopic() {
        return TopicBuilder.name(UPDATED_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

}
