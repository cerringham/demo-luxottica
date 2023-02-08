package it.bitrock.demoluxottica.controller;

import it.bitrock.demoluxottica.config.FhirContextSettings;
import it.bitrock.demoluxottica.service.DiagnosticReportService;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/diagnostic-report")
public class DiagnosticReportController {

    @Autowired
    DiagnosticReportService service;

    @GetMapping
    public ResponseEntity<String> getAllDiagnosticReports() {
        Optional<List<DiagnosticReport>> diagnosticReports = service.getAllDiagnosticReports();
        if(diagnosticReports.isPresent()){
            return ResponseEntity
                    .ok()
                    .body(diagnosticReports.get().toString());
        }
        return ResponseEntity.status(404).build();
    }

    // testId 1129
    @GetMapping("/{id}")
    public ResponseEntity<String> getDiagnosticReportById(@PathVariable String id) {
        if(service.getDiagnosticReportById(id).isPresent()){
            return ResponseEntity
                    .ok()
                    .body(FhirContextSettings
                            .toString(service.getDiagnosticReportById(id).get()));
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping
    public ResponseEntity<String> saveDiagnosticReport(@PathVariable String id) {
        if(service.getDiagnosticReportById(id).isPresent()){
            return ResponseEntity
                    .ok()
                    .body(FhirContextSettings
                            .toString(service.getDiagnosticReportById(id).get()));
        }
        return ResponseEntity.status(404).build();
    }



    @GetMapping("/{id}/patient")
    public ResponseEntity<String>  getPatientByDiagnosticReportId(@PathVariable String id) {
        if(service.getPatientByDiagnosticReportId(id).isPresent()){
            return ResponseEntity
                    .ok()
                    .body(FhirContextSettings
                            .toString(service.getPatientByDiagnosticReportId(id).get()));
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{id}/encounter")
    public ResponseEntity<String>  getEncounterByDiagnosticReportId(@PathVariable String id) {
        if(service.getEncounterByDiagnosticReportId(id).isPresent()){
            return ResponseEntity
                    .ok()
                    .body(FhirContextSettings
                            .toString(service.getEncounterByDiagnosticReportId(id).get()));
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{id}/performer")
    public ResponseEntity<String> getPerformerByDiagnosticReportId(@PathVariable String id) {
        Optional<List<Reference>> performerList = service.getPerformerByDiagnosticReportId(id);
        if(performerList.isPresent()){
            return ResponseEntity
                    .ok()
                    .body(performerList.get().toString());
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{id}/result")
    public ResponseEntity<String>  getResultByDiagnosticReportId(@PathVariable String id) {
        Optional<List<Reference>> resultReferenceList = service.getResultByDiagnosticReportId(id);
        if(resultReferenceList.isPresent()){
            return ResponseEntity
                    .ok()
                    .body(resultReferenceList.toString());
        }
        return ResponseEntity.status(404).build();
    }
}
