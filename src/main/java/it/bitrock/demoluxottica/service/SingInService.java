package it.bitrock.demoluxottica.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import it.bitrock.demoluxottica.dto.RegistrazioneDTO;
import it.bitrock.demoluxottica.model.Utente;
import it.bitrock.demoluxottica.repository.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.codesystems.ContactPointSystem;
import org.hl7.fhir.r4.model.codesystems.ContactPointUse;
import org.hl7.fhir.utilities.graphql.StringValue;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.Enumeration;

@Service
@Slf4j
public class SingInService {
    private final UtenteRepository utenteRepository;

    public SingInService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    /*TODO se non esiste creare un nuovo Patient valorizzato con i campi della registrazione,
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
        Utente utente = new Utente();
        BeanUtils.copyProperties(registrazioneDTO, utente);
        Patient patient = new Patient();

        patient.setId(String.valueOf(registrazioneDTO.getId()));
        patient.addIdentifier(new Identifier()
                .setSystem(String.valueOf(registrazioneDTO.getId()))
                .setValue("12345"));

        patient.addName(new HumanName()
                .setFamily(registrazioneDTO.getCognome())
                .setGiven(Arrays.asList(new StringType(registrazioneDTO.getNome()))));

        patient.addTelecom(new ContactPoint()
                .setValue(registrazioneDTO.getEmail())
                .setSystem(ContactPoint.ContactPointSystem.EMAIL));

        Date dataFine = Date.from(registrazioneDTO.getFineSessione().atZone(ZoneId.systemDefault()).toInstant());
        Date dataInizio = Date.from(registrazioneDTO.getInizioSessione().atZone(ZoneId.systemDefault()).toInstant());
        patient.addExtension().setUrl("http://example.org/fhir/StructureDefinition/patient-treatmentPeriod")
                .setValue(new Period().setStart(dataInizio).setEnd(dataFine));

        log.info("Registration: {}", registrazioneDTO);
        Boolean created =  client.create().resource(patient).execute().getCreated();
        Utente u = utenteRepository.save(utente);
        log.info("Created in Fhir: {}, with ID: {}", created, registrazioneDTO.getId());
        log.info("Created in DB: {}, with ID: {}", u, u.getId());
        if(created == true) {
            return ResponseEntity.ok(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
