package it.bitrock.demoluxottica.controller;

import it.bitrock.demoluxottica.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    @Autowired
    PatientService service;

    @GetMapping("/patient/{id}")
    public String getPatientByStringId(@PathVariable String id) {
        return service.getPatientByStringId(id);
    }

}
