package it.bitrock.demoluxottica.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import it.bitrock.demoluxottica.dto.RegistrazioneDTO;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.codesystems.ContactPointSystem;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class SingInService {

    public ResponseEntity<?> singIn(RegistrazioneDTO registrazioneDTO){
        FhirContext ctx = FhirContext.forR4();
//        // Create a client
//        IGenericClient client = ctx.newRestfulGenericClient("https://hapi.fhir.org/baseR4");
//        // Read a patient with the given ID
//        Patient patient = client.read().resource(Patient.class).withId(id).execute();
        List<HumanName> humanNameList =
                Arrays.asList(new HumanName().setFamily(registrazioneDTO.getNome() + " " + registrazioneDTO.getCognome()));
        //FIXME capire come settare l'E-MAIL
        List<ContactPoint> contactPoints =
                Arrays.asList(new ContactPoint().setValue(registrazioneDTO.getEmail()));
        Patient patient = new Patient();
        patient.setName(humanNameList);
        patient.setTelecom(contactPoints);
        log.info("Registration: {}", registrazioneDTO);
        return ResponseEntity.ok(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient));
    }

}
