package it.bitrock.demoluxottica.controller;

import it.bitrock.demoluxottica.models.enumerations.FhirContextEnum;
import it.bitrock.demoluxottica.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Optional<Patient> getPatientByStringId(@PathVariable String id) {
        log.info("---------------------------     Called getPatientByStringId Endpoint     ---------------------------");
        return service.getPatientByStringId(id, FhirContextEnum.R4);
    }

}
