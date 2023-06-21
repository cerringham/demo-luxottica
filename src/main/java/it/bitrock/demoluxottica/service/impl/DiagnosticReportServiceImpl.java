package it.bitrock.demoluxottica.service.impl;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.IClientExecutable;
import it.bitrock.demoluxottica.integration.kafka.producer.ProducerDiagnosticReportService;
import it.bitrock.demoluxottica.mapper.DiagnosticReportMapper;
import it.bitrock.demoluxottica.models.dto.DiagnosticReportDTO;
import it.bitrock.demoluxottica.service.DiagnosticReportService;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.springframework.stereotype.Service;

@Service
public class DiagnosticReportServiceImpl implements DiagnosticReportService {

    private final IGenericClient fhirClient;
    private final ProducerDiagnosticReportService producerDiagnosticReportService;
    private static final DiagnosticReportMapper mapper = DiagnosticReportMapper.INSTANCE;

    public DiagnosticReportServiceImpl(IGenericClient fhirClient, ProducerDiagnosticReportService producerDiagnosticReportService) {
        this.fhirClient = fhirClient;
        this.producerDiagnosticReportService = producerDiagnosticReportService;
    }

    public DiagnosticReportDTO getDiagnosticReportById(String id) {
        return Optional.ofNullable(id)
                .filter(i -> !i.isEmpty())
                .map(r -> fhirClient.read().resource(DiagnosticReport.class))
                .map(r -> r.withId(id))
                .map(IClientExecutable::execute)
                .map(d -> {
                    producerDiagnosticReportService.findDiagnosticRecordById(d);
                    return mapper.mapDiagnosticReport(d);
                })
                .orElseThrow(BadRequestException::new);
    }
}
