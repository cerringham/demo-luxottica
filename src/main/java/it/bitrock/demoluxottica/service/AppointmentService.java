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
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AppointmentService {

    private final static String version = "1";

    public ResponseEntity<?> addAppointment(AppointmentDTO appointmentDTO){
        Appointment appointment = new Appointment();

        appointment.setId(appointmentDTO.getId());
        appointment.setMeta(new Meta()
                .setVersionId(version)
                .setLastUpdated(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())));

        appointment.setDescription(appointmentDTO.getDescription());
        appointment.setStatus(Appointment.AppointmentStatus.BOOKED);

        appointment.setStart(Date.from(appointmentDTO.getStart().atZone(ZoneId.systemDefault()).toInstant()));
        appointment.setEnd(Date.from(appointmentDTO.getEnd().atZone(ZoneId.systemDefault()).toInstant()));

        addReference(appointment, appointmentDTO.getId());
        addParticipant(appointment, appointmentDTO.getParticipant());

        boolean created = FhirContextSettings.r4_client.create().resource(appointment).execute().getCreated();
        if(created){
            log.info("\nAppointment: id {},\nParticipant {}", appointment.getId(),
                    appointment.getParticipant().stream().map(p -> p.getActor()));
            return ResponseEntity.ok(FhirContextSettings.getParser().encodeResourceToString(appointment));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity<?> addAppointment() {
        Appointment appointment = new Appointment();

        appointment.setId("2345-3455-5555-8966");
        LocalDateTime now = LocalDateTime.now();
        appointment.setMeta(new Meta()
                .setVersionId(version)
                .setLastUpdated(Date.from(now.atZone(ZoneId.systemDefault()).toInstant())));
        appointment.setDescription("eye injuries");
        appointment.setStatus(Appointment.AppointmentStatus.BOOKED);

        LocalDateTime start = LocalDateTime.of(2023, 10, 22, 10, 10);
        appointment.setStart(Date.from(start.atZone(ZoneId.systemDefault()).toInstant()));
        LocalDateTime end = LocalDateTime.of(2023, 10, 22, 12, 10);
        appointment.setEnd(Date.from(end.atZone(ZoneId.systemDefault()).toInstant()));

        addReference(appointment, "Patient/example");
        addParticipant(appointment, Arrays.asList("Dr. Doe"));

        boolean created = FhirContextSettings.r4_client.create().resource(appointment).execute().getCreated();
        if(created){
            log.info("\nAppointment: id {},\nParticipant {}", appointment.getId(),
                    appointment.getParticipant().stream().map(p -> p.getActor()));
            return ResponseEntity.ok(FhirContextSettings.getParser().encodeResourceToString(appointment));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private void addReference(Appointment appointment, String id) {
        Reference patientRef = new Reference();
        //we cannot add an appointment without an exists Patient/id
        patientRef.setReference(id);
        Appointment.AppointmentParticipantComponent patientAppointment =
                new Appointment.AppointmentParticipantComponent();
        patientAppointment
                .setActor(patientRef)
                .setStatus(Appointment.ParticipationStatus.ACCEPTED);
        appointment.addParticipant(patientAppointment);
    }

    private void addParticipant(Appointment appointment, List<String> participants) {
        for(String participant : participants) {
            appointment.addParticipant(new Appointment.AppointmentParticipantComponent()
                    .setActor(new Reference().setDisplay(participant))
                    .setStatus(Appointment.ParticipationStatus.ACCEPTED));
        }
    }

}
