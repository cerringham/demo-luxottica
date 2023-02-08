package it.bitrock.demoluxottica.service;

import com.github.javafaker.Faker;
import com.github.javafaker.Faker;
import it.bitrock.demoluxottica.config.FhirContextSettings;
import it.bitrock.demoluxottica.models.dto.PatientDTO;
import it.bitrock.demoluxottica.models.enumerations.FhirContextEnum;
import it.bitrock.demoluxottica.utils.FhirUtils;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PatientService {

    private final String URL_TREATMENT_PERIOD = "http://example.org/fhir/StructureDefinition/patient-treatmentPeriod";

    public List<Patient> getAllPatient(){
        return FhirUtils.getStreamOfAll(Patient.class)
                .map(bundleEntryComponent -> (Patient) bundleEntryComponent.getResource())
                .collect(Collectors.toList());
    }

    public Optional<Patient> getPatientByStringId(String id, FhirContextEnum fhirContext) {
        if(id == null || id.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(FhirContextSettings.getResource(Patient.class, fhirContext).withId(id).execute());
    }

    public ResponseEntity<?> addPatient(PatientDTO patientDTO){
        Patient patient = new Patient();

        patient.setId(patientDTO.getId());
        patient.addIdentifier(new Identifier()
                .setSystem(String.valueOf(String.valueOf(UUID.randomUUID())))
                .setValue(String.valueOf(new Random().nextInt())));

        patient.addName(new HumanName()
                .setFamily(patientDTO.getFamilyName())
                .setGiven(Arrays.asList(new StringType(patientDTO.getGivenName()))));

        addTelecom(patient, patientDTO.getEmail(), patientDTO.getTelephone());

        addTreatment(patient, patientDTO.getStart(), patientDTO.getEnd());

        //TODO ADD LOGIC

        Boolean created = FhirContextSettings.r4_client.create().resource(patient).execute().getCreated();
        if (created == true) {
            log.info("Created in Fhir: {}, with ID: {}", true, patient.getId());
            return ResponseEntity.ok(FhirContextSettings.getParser().encodeResourceToString(patient));
        } else {
            log.info("Created in Fhir: {}, with ID: {}", false, patient.getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity<?> addPatient() {

        Patient patient = new Patient();

        patient.setId(String.valueOf(UUID.randomUUID()));
        patient.addIdentifier(new Identifier()
                .setSystem(String.valueOf(String.valueOf(UUID.randomUUID())))
                .setValue(String.valueOf(new Random().nextInt())));

        patient.addName(new HumanName()
                .setFamily(new Faker().name().lastName())
                .setGiven(Arrays.asList(new StringType(new Faker().name().firstName()))));

        addTelecom(patient, "esempio@libero.it", "+39 55534590");

        Random r = new Random();
        long t1 = System.currentTimeMillis() + r.nextInt();
        long t2 = t1 + 2 * 60 * 1000 + r.nextInt(60 * 1000) + 1;
        Date start = new Date(t1);
        Date end = new Date(t2);
        patient.addExtension()
                .setUrl(URL_TREATMENT_PERIOD)
                .setValue(new Period()
                        .setStart(start)
                        .setEnd(end));

        Boolean created = FhirContextSettings.r4_client.create().resource(patient).execute().getCreated();
        if (created == true) {
            log.info("Created in Fhir: {}, with ID: {}", true, patient.getId());
            return ResponseEntity.ok(FhirContextSettings.getParser().encodeResourceToString(patient));
        } else {
            log.info("Created in Fhir: {}, with ID: {}", false, patient.getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private void addTelecom(Patient patient, String email, String telephone) {
        patient.addTelecom(new ContactPoint()
                .setValue(new Faker().expression(email))
                .setSystem(ContactPoint.ContactPointSystem.EMAIL));
        patient.addTelecom(new ContactPoint()
                .setValue(new Faker().expression(telephone))
                .setSystem(ContactPoint.ContactPointSystem.PHONE));
    }

    private void addTreatment(Patient patient, LocalDateTime start, LocalDateTime end) {
        patient.addExtension()
                .setUrl(URL_TREATMENT_PERIOD)
                .setValue(new Period()
                        .setStart(Date.from(start.atZone(ZoneId.systemDefault()).toInstant()))
                        .setEnd(Date.from(end.atZone(ZoneId.systemDefault()).toInstant())));
    }

}
