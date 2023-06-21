package it.bitrock.demoluxottica.controller;

import it.bitrock.demoluxottica.models.dto.DiagnosticReportDTO;
import it.bitrock.demoluxottica.service.impl.DiagnosticReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosticReportController {

    @Autowired
    DiagnosticReportServiceImpl service;

    // testId 1129
    @GetMapping("/diagnostic-report/{id}")
    public ResponseEntity<DiagnosticReportDTO> getDiagnosticReportById(@PathVariable String id) {
        return new ResponseEntity<>(service.getDiagnosticReportById(id), HttpStatus.OK);
    }
}
