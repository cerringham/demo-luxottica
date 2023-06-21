package it.bitrock.demoluxottica.controller;

import it.bitrock.demoluxottica.models.enumerations.FhirContextEnum;
import it.bitrock.demoluxottica.service.PatientService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/patient")
public class PatientController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PatientController.class);
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

}
