package it.bitrock.demoluxottica.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import it.bitrock.demoluxottica.dto.RegistrazioneDTO;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.codesystems.ContactPointSystem;
import org.hl7.fhir.r4.model.codesystems.ContactPointUse;
import org.hl7.fhir.utilities.graphql.StringValue;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@Service
@Slf4j
public class SingInService {

    /*TODO se non esiste creare un nuovo Patient con valorizzato con i capi della registrazione
       se esiste invece recuperare il Patient da Fhir (probabilmente con JPA), verificare i dati
       e aggiornarli.
       Il recupero credo si possa fare tramite CF (per gli Americani "codice di previdenza sociale")*/
    public ResponseEntity<?> singIn(RegistrazioneDTO registrazioneDTO){
        FhirContext ctx = FhirContext.forR4();
        // Create a client
        IGenericClient client = ctx.newRestfulGenericClient("https://hapi.fhir.org/baseR4");

//        if(patientRepository.exixtsByCF/ID(...) {
        // Read a patient with the given ID
//        patient = patientRepository.findByID
//        Patient patientFind = client.read().resource(Patient.class).withId(id).execute();
//        }
        Patient patient = new Patient();
        patient.addName(new HumanName()
                .setFamily(registrazioneDTO.getCognome())
                .setGiven(Arrays.asList(new StringType(registrazioneDTO.getNome()))));
        patient.addTelecom(new ContactPoint()
                .setValue(registrazioneDTO.getEmail())
                .setSystem(ContactPoint.ContactPointSystem.EMAIL));
//        patient.setId(String.valueOf(registrazioneDTO.getId()));
        patient.addIdentifier(new Identifier().setSystem(String.valueOf(registrazioneDTO.getId())));
        log.info("Registration: {}", registrazioneDTO);
        Boolean created =  client.create().resource(patient).execute().getCreated();
        log.info("Created : {}, with ID: {}", created, registrazioneDTO.getId());
        return ResponseEntity.ok(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient));
    }

}
