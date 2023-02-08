package it.bitrock.demoluxottica.controller;

import it.bitrock.demoluxottica.models.enumerations.FhirContextEnum;
import it.bitrock.demoluxottica.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientService service;

    @GetMapping("/all")
    public String getAllPatient(){
        log.info("---------------------------     Called getAllPatient Endpoint     ---------------------------");
        return service.getAllPatient().toString();
    }

    @GetMapping("/getById/{id}")
    public String getPatientByStringId(@PathVariable String id) {
        log.info("---------------------------     Called getPatientByStringId Endpoint     ---------------------------");
        return service.getPatientByStringId(id, FhirContextEnum.R4);
    }

    @GetMapping("/add_patient")
    public ResponseEntity<?> addPatient(){
        return service.addPatient();
    }

}
