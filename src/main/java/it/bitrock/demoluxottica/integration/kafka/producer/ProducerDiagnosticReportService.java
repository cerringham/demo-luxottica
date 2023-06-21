package it.bitrock.demoluxottica.integration.kafka.producer;

import it.bitrock.demoluxottica.config.KafkaDiagnosticReportConfiguration;
import it.bitrock.demoluxottica.mapper.DiagnosticReportMapper;
import it.bitrock.demoluxottica.models.avro.DiagnosticReportRecord;
import org.apache.kafka.common.KafkaException;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerDiagnosticReportService {

    private final KafkaTemplate<String, DiagnosticReportRecord> template;
    private static final DiagnosticReportMapper mapper = DiagnosticReportMapper.INSTANCE;

    @Autowired
    public ProducerDiagnosticReportService(KafkaTemplate<String, DiagnosticReportRecord> template) {
        this.template = template;
    }

    public void findDiagnosticRecordById(DiagnosticReport dr) {
        sendDiagnosticReport(dr);
    }


    private void sendDiagnosticReport(DiagnosticReport dr) {
        DiagnosticReportRecord drRecord = mapper.fromDiagnosticReportToDiagnosticReportRecord(dr);
        try {
            template.send(KafkaDiagnosticReportConfiguration.FIND_TOPIC, dr.getId(), drRecord);
        } catch (Exception e) {
            throw new KafkaException(e.getMessage());
        }
    }
}
