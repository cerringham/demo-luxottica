package it.bitrock.demoluxottica.controller;

import it.bitrock.demoluxottica.service.AppointmentService;
import org.hl7.fhir.r4.model.Appointment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    AppointmentService service;

    @PostMapping("/create")
    public ResponseEntity<?> addAppointment(@ModelAttribute Appointment appointment){
        return service.addAppointment(appointment);
    }

    @GetMapping("/create")
    public ResponseEntity<?> addAppointment(){
        return service.addAppointment();
    }

}
