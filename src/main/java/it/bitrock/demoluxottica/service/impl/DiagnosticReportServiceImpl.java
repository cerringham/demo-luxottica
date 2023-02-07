package it.bitrock.demoluxottica.service.impl;


import it.bitrock.demoluxottica.config.FhirContextSettings;
import it.bitrock.demoluxottica.models.enumerations.FhirContextEnum;
import it.bitrock.demoluxottica.service.DiagnosticReportService;
import it.bitrock.demoluxottica.service.EncounterService;
import it.bitrock.demoluxottica.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j

public class DiagnosticReportServiceImpl implements DiagnosticReportService {
    @Autowired
    PatientService patientService;
    @Autowired
    EncounterService encounterService;
    public Optional<DiagnosticReport> getDiagnosticReportById(String id) {
        if(id == null || id.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(FhirContextSettings.getResource(DiagnosticReport.class, FhirContextEnum.R4).withId(id).execute());
    }

    public Optional<Patient> getPatientByDiagnosticReportId(String diagnosticReportId){
        if(diagnosticReportId == null || diagnosticReportId.isEmpty()) {
            return Optional.empty();
        }
        String patientId = FhirContextSettings
                .getResource(DiagnosticReport.class, FhirContextEnum.R4)
                .withId(diagnosticReportId)
                .execute()
                .getSubject()
                .getReference()
                .toString()
                .replace("Patient/", "");
        return patientService.getPatientByStringId(patientId, FhirContextEnum.R4);
    }

    public Optional<Encounter> getEncounterByDiagnosticReportId(String diagnosticReportId){
        if(diagnosticReportId == null || diagnosticReportId.isEmpty()) {
            return Optional.empty();
        }
        String encounterId  = FhirContextSettings
                .getResource(Encounter.class, FhirContextEnum.R4)
                .withId(diagnosticReportId)
                .execute()
                .getSubject()
                .getReference()
                .toString()
                .replace("Encounter/", "");
        return encounterService.getEncounterById(encounterId);
    }

    public Optional<List<Reference>> getPerformerByDiagnosticReportId(String diagnosticReportId){
        if(diagnosticReportId == null || diagnosticReportId.isEmpty()) {
            return Optional.empty();
        }
        List<Reference> performers = FhirContextSettings
                .getResource(DiagnosticReport.class, FhirContextEnum.R4)
                .withId(diagnosticReportId)
                .execute()
                .getPerformer();
        return Optional.of(performers);
    }

    public Optional<List<Reference>> getResultByDiagnosticReportId(String diagnosticReportId){
        if(diagnosticReportId == null || diagnosticReportId.isEmpty()) {
            return Optional.empty();
        }
        List<Reference> performers = FhirContextSettings
                .getResource(DiagnosticReport.class, FhirContextEnum.R4)
                .withId(diagnosticReportId)
                .execute()
                .getResult();
        return Optional.of(performers);
    }
}
