package it.bitrock.demoluxottica.integration.kafka.producer;

import java.util.concurrent.ExecutionException;

import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import it.bitrock.demoluxottica.integration.kafka.model.DiagnosticReportSnapshot;
import it.bitrock.demoluxottica.mapper.DiagnosticReportMapper;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProducerDiagnosticReportService {

    private final ProducerService<DiagnosticReportSnapshot> producerService;
    private final String topicPatientSnapshot;
    private final IGenericClient fhirHttpClient;
    private static final DiagnosticReportMapper mapper = DiagnosticReportMapper.INSTANCE;
    private static final Logger log = LoggerFactory.getLogger(ProducerDiagnosticReportService.class);

    @Autowired
    public ProducerDiagnosticReportService(ProducerService<DiagnosticReportSnapshot> producerService, @Value("${kafka.topics.dr-snapshot}") String topicPatientSnapshot, IGenericClient fhirHttpClient) {
        this.producerService = producerService;
        this.topicPatientSnapshot = topicPatientSnapshot;
        this.fhirHttpClient = fhirHttpClient;
    }

    public void findDiagnosticRecordById(DiagnosticReport dr) {
        sendDiagnosticReport(dr);
    }


    private void sendDiagnosticReport(DiagnosticReport dr) {
        DiagnosticReportSnapshot drSnapshot = mapper.fromFhirToDiagnosticReportSnapshot(serializeObject(dr));
        try {
            producerService.sendMessage(dr.getId(), drSnapshot, topicPatientSnapshot);
            log.debug("Sent message to kafka. DiagnosticReport -> with Id: {}",
                    dr.getId());
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Cannot send message to kafka. DiagnosticReport -> with Id: {}",
                    dr.getId());
        }
    }

    private String serializeObject(Resource resource) {
        IParser parser = fhirHttpClient.getFhirContext().newJsonParser();
        return parser.encodeResourceToString(resource);
    }
}
