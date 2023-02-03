package it.bitrock.demoluxottica.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Appointment;
import org.hl7.fhir.r4.model.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@Slf4j
public class AppointmentService {

    private FhirContext ctx = FhirContext.forR4();
    private IGenericClient getClient(){
        return ctx.newRestfulGenericClient("https://hapi.fhir.org/baseR4");
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
        boolean created = getClient().create().resource(appointment).execute().getCreated();
        if(created == true){
            return ResponseEntity.ok(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(appointment));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
