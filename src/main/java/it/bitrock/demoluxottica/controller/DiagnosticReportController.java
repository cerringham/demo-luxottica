package it.bitrock.demoluxottica.controller;

import it.bitrock.demoluxottica.service.DiagnosticReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosticReportController {

    @Autowired
    DiagnosticReportService service;

    //1129
    @GetMapping("/diagnostic-report/{id}")
    public String getDiagnosticReportById(@PathVariable String id) {
        return service.getDiagnosticReportById(id);
    }
}
