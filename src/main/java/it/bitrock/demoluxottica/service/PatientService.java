package it.bitrock.demoluxottica.service;

import com.github.javafaker.Faker;
import com.github.javafaker.Faker;
import it.bitrock.demoluxottica.config.FhirContextSettings;
import it.bitrock.demoluxottica.models.enumerations.FhirContextEnum;
import it.bitrock.demoluxottica.utils.FhirUtils;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PatientService {

    private final String urlTreatmentPeriod = "http://example.org/fhir/StructureDefinition/patient-treatmentPeriod";

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

    public ResponseEntity<?> addPatient() {

        Patient patient = new Patient();

        patient.setId(String.valueOf(UUID.randomUUID()));
        patient.addIdentifier(new Identifier()
                .setSystem(String.valueOf(String.valueOf(UUID.randomUUID())))
                .setValue(String.valueOf(new Random().nextInt())));

        patient.addName(new HumanName()
                .setFamily(new Faker().name().lastName())
                .setGiven(Arrays.asList(new StringType(new Faker().name().firstName()))));

        patient.addTelecom(new ContactPoint()
                .setValue(new Faker().expression("essempio@email.com"))
                .setSystem(ContactPoint.ContactPointSystem.EMAIL));

        Random r = new Random();
        long t1 = System.currentTimeMillis() + r.nextInt();
        long t2 = t1 + 2 * 60 * 1000 + r.nextInt(60 * 1000) + 1;
        Date start = new Date(t1);
        Date end = new Date(t2);
        patient.addExtension()
                .setUrl(urlTreatmentPeriod)
                .setValue(new Period()
                        .setStart(start)
                        .setEnd(end));

        log.info("Patient: {}", patient);
//        Boolean created =  client.create().resource(patient).execute().getCreated();
        Boolean created = FhirContextSettings.r4_client.create().resource(patient).execute().getCreated();
        if (created == true) {
            log.info("Created in Fhir: {}, with ID: {}", true, patient.getId());
            return ResponseEntity.ok(FhirContextSettings.getParser().encodeResourceToString(patient));
        } else {
            log.info("Created in Fhir: {}, with ID: {}", false, patient.getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
