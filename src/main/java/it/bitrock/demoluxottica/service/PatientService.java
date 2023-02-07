package it.bitrock.demoluxottica.service;

import it.bitrock.demoluxottica.config.FhirContextSettings;
import it.bitrock.demoluxottica.models.enumerations.FhirContextEnum;
import it.bitrock.demoluxottica.utils.FhirUtils;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PatientService {

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

    /*
     public ResponseEntity<?> singIn(RegistrationDTO registrationDTO){
        FhirContext ctx = FhirContext.forR4();
        // Create a client
        IGenericClient client = ctx.newRestfulGenericClient("https://hapi.fhir.org/baseR4");

        Utente utente = new Utente();
        BeanUtils.copyProperties(registrationDTO, utente);
        Patient patient = new Patient();

        patient.setId(String.valueOf(registrationDTO.getId()));
        patient.addIdentifier(new Identifier()
                .setSystem(String.valueOf(registrationDTO.getId()))
                .setValue("12345"));

        patient.addName(new HumanName()
                .setFamily(registrationDTO.getSurname())
                .setGiven(Arrays.asList(new StringType(registrationDTO.getName()))));

        patient.addTelecom(new ContactPoint()
                .setValue(registrationDTO.getEmail())
                .setSystem(ContactPoint.ContactPointSystem.EMAIL));

        Date dataFine = Date.from(registrationDTO.getEndSession().atZone(ZoneId.systemDefault()).toInstant());
        Date dataInizio = Date.from(registrationDTO.getStartSession().atZone(ZoneId.systemDefault()).toInstant());
        patient.addExtension().setUrl("http://example.org/fhir/StructureDefinition/patient-treatmentPeriod")
                .setValue(new Period().setStart(dataInizio).setEnd(dataFine));

        log.info("Registration: {}", registrationDTO);
        Boolean created =  client.create().resource(patient).execute().getCreated();
        Utente u = utenteRepository.save(utente);
        log.info("Created in Fhir: {}, with ID: {}", created, registrationDTO.getId());
        log.info("Created in DB: {}, with ID: {}", u, u.getId());
        if(created == true) {
            return ResponseEntity.ok(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
     */
}
