package it.bitrock.demoluxottica.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaDiagnosticReportConfiguration {
    public static final String FIND_TOPIC = "diagnostic-report-found";
    @Bean
    public NewTopic patientAddedTopic() {
        return TopicBuilder.name(FIND_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

}
