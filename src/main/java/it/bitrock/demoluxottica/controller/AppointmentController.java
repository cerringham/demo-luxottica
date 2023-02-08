package it.bitrock.demoluxottica.controller;

import it.bitrock.demoluxottica.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/appointment")
@Slf4j
public class AppointmentController {

    @Autowired
    AppointmentService service;

    @PostMapping(value = "/create")
    public ResponseEntity<?> addAppointment(@RequestBody AppointmentDTO appointmentDTO){
        log.info("controller");
        return service.addAppointment(appointmentDTO);
    }

    @GetMapping("/create")
    public ResponseEntity<?> addAppointment(){
        return service.addAppointment();
    }

}
