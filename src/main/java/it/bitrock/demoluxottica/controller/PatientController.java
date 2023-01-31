package it.bitrock.demoluxottica.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.bitrock.demoluxottica.service.PatientService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paziente")
@Tag(name = "Patient Controller", description = "Manage of the patient")
public class PatientController {

    @Autowired
    PatientService service;

    @GetMapping("/{id}")
    public String getPatientByStringId(@PathVariable String id) {
        return service.getPatientByStringId(id);
    }



}
