package it.bitrock.demoluxottica.service;

import it.bitrock.demoluxottica.config.FhirContextSettings;
import it.bitrock.demoluxottica.models.dto.AppointmentDTO;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Appointment;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AppointmentService {

    private static String version = "1";

    public ResponseEntity<?> addAppointment(AppointmentDTO appointmentDTO){
        Appointment appointment = new Appointment();
        appointment.setId(appointmentDTO.getId());
        LocalDateTime now = LocalDateTime.now();
        appointment.setMeta(
                new Meta()
                        .setVersionId("1")
                        .setLastUpdated(Date.from(now.atZone(ZoneId.systemDefault()).toInstant())));
        appointment.setDescription(appointmentDTO.getDescription());
        appointment.setStatus(Appointment.AppointmentStatus.BOOKED);

        appointment.setStart(Date.from(appointmentDTO.getStart().atZone(ZoneId.systemDefault()).toInstant()));
        appointment.setEnd(Date.from(appointmentDTO.getEnd().atZone(ZoneId.systemDefault()).toInstant()));

        Reference patientRef = new Reference();
        //we cannot add an appointment without an exists Patient/id
        patientRef.setReference(appointmentDTO.getPatientID());
        Appointment.AppointmentParticipantComponent patientAppointment =
                new Appointment.AppointmentParticipantComponent();
        patientAppointment
                .setActor(patientRef)
                .setStatus(Appointment.ParticipationStatus.ACCEPTED);
        appointment.addParticipant(patientAppointment);

        for(String participant : appointmentDTO.getParticipant()) {
            appointment.addParticipant(new Appointment.AppointmentParticipantComponent()
                    .setActor(new Reference().setDisplay(participant))
                    .setStatus(Appointment.ParticipationStatus.ACCEPTED));
        }

        log.info("  \nAppointment: id {},\nParticipant {}", appointment.getId(),
                appointment.getParticipant().stream().map(p -> p.getActor()));
        boolean created = FhirContextSettings.r4_client.create().resource(appointment).execute().getCreated();
        if(created){
            return ResponseEntity.ok(FhirContextSettings.getParser().encodeResourceToString(appointment));
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

//        boolean created = getClient(url).create().resource(appointment).execute().getCreated();
        boolean created = FhirContextSettings.r4_client.create().resource(appointment).execute().getCreated();
        if(created){
            return ResponseEntity.ok(FhirContextSettings.getParser().encodeResourceToString(appointment));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
