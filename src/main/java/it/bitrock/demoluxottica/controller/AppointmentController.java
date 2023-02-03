package it.bitrock.demoluxottica.controller;

import it.bitrock.demoluxottica.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
@Slf4j
public class AppointmentController {

    @Autowired
    AppointmentService service;

    @PostMapping("/create")
    public ResponseEntity<?> addAppointment(@ModelAttribute Appointment appointment){
        return service.addAppointment(appointment);
    }

}
