package it.bitrock.demoluxottica.controller;

import it.bitrock.demoluxottica.config.FhirContextSettings;
import it.bitrock.demoluxottica.service.DiagnosticReportService;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosticReportController {

    @Autowired
    DiagnosticReportService  service;

    // testId 1129
    @GetMapping("/diagnostic-report/{id}")
    public ResponseEntity<String> getDiagnosticReportById(@PathVariable String id) {
        if(service.getDiagnosticReportById(id).isPresent()){
            return ResponseEntity
                    .ok()
                    .body(FhirContextSettings
                            .optionalToString(service.getDiagnosticReportById(id)));
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/diagnostic-report/{id}/patient")
    public String getPatientByDiagnosticReportId(@PathVariable String id) {
        return service.getPatientByDiagnosticReportId(id).toString();
    }

    @GetMapping("/diagnostic-report/{id}/encounter")
    public String getEncounterByDiagnosticReportId(@PathVariable String id) {
        return service.getEncounterByDiagnosticReportId(id).toString();
    }

    @GetMapping("/diagnostic-report/{id}/performer")
    public String getPerformerByDiagnosticReportId(@PathVariable String id) {
        return service.getPerformerByDiagnosticReportId(id).toString();
    }

    @GetMapping("/diagnostic-report/{id}/result")
    public String getResultByDiagnosticReportId(@PathVariable String id) {
        return service.getResultByDiagnosticReportId(id).toString();
    }
}
