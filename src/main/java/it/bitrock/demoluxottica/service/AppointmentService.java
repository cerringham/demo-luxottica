package it.bitrock.demoluxottica.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Appointment;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

@Service
public class AppointmentService {
    private static final Logger log = LoggerFactory.getLogger(AppointmentService.class);

    private FhirContext ctx = FhirContext.forR4();
    private final String url = "https://hapi.fhir.org/baseR4";
    private IGenericClient getClient(String url){
        return ctx.newRestfulGenericClient(url);
    }

    public ResponseEntity<?> addAppointment(Appointment appointment){
        // Set the relevant details for the appointment
        appointment.setDescription("eye injuries");
        appointment.setStatus(Appointment.AppointmentStatus.BOOKED);

        Reference patientRef = new Reference();
        //we cannot add an appointment without an exists Patient/id
        patientRef.setReference("Patient/example");
        Appointment.AppointmentParticipantComponent patientAppointment =
                new Appointment.AppointmentParticipantComponent();
        patientAppointment.setActor(patientRef);
        appointment.addParticipant(patientAppointment);
        appointment.addParticipant(new Appointment.AppointmentParticipantComponent()
                .setActor(new Reference()
                        .setDisplay("Dr Doe")));
        log.info("Appointment: {}", appointment);
        for (Field field : appointment.getClass().getDeclaredFields()) {
            log.info("field: {}, {}" + field.getName(), field.getClass().getDeclaredFields());
        }
        boolean created = getClient(url).create().resource(appointment).execute().getCreated();
        if(created){
            return ResponseEntity.ok(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(appointment));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity<?> addAppointment() {
        Appointment appointment = new Appointment();
        // Set the relevant details for the appointment
        appointment.setId("2345-3455-5555-8966");
        LocalDateTime now = LocalDateTime.now();
        appointment.setMeta(
                new Meta()
                        .setVersionId("1")
                        .setLastUpdated(Date.from(now.atZone(ZoneId.systemDefault()).toInstant())));
        appointment.setDescription("eye injuries");
        appointment.setStatus(Appointment.AppointmentStatus.BOOKED);

        LocalDateTime start = LocalDateTime.of(2023, 10, 22, 10, 10);
        appointment.setStart(Date.from(start.atZone(ZoneId.systemDefault()).toInstant()));
        LocalDateTime end = LocalDateTime.of(2023, 10, 22, 12, 10);
        appointment.setEnd(Date.from(end.atZone(ZoneId.systemDefault()).toInstant()));

        Reference patientRef = new Reference();
        //we cannot add an appointment without an exists Patient/id
        patientRef.setReference("Patient/example");
        Appointment.AppointmentParticipantComponent patientAppointment =
                new Appointment.AppointmentParticipantComponent();
        patientAppointment.setActor(patientRef)
                .setStatus(Appointment.ParticipationStatus.ACCEPTED);
        appointment.addParticipant(patientAppointment);
        appointment.addParticipant(new Appointment.AppointmentParticipantComponent()
                .setActor(new Reference()
                        .setDisplay("Dr Doe"))
                .setStatus(Appointment.ParticipationStatus.ACCEPTED));
        log.info("Appointment: {}", appointment);

        for (Field field : appointment.getClass().getDeclaredFields()) {
            log.info("field: {}, {}" + field.getName(), Arrays.stream(field.getClass().getDeclaredFields()).findAny());
        }

        boolean created = getClient(url).create().resource(appointment).execute().getCreated();
        if(created){
            return ResponseEntity.ok(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(appointment));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
