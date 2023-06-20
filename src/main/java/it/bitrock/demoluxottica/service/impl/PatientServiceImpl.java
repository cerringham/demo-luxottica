package it.bitrock.demoluxottica.service.impl;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.server.exceptions.ResourceGoneException;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import it.bitrock.demoluxottica.exception.NotFoundException;
import it.bitrock.demoluxottica.exception.PatientCreationException;
import it.bitrock.demoluxottica.mapper.PatientMapper;
import it.bitrock.demoluxottica.models.PatientEntity;
import it.bitrock.demoluxottica.repository.PatientRepository;
import it.bitrock.demoluxottica.service.KafkaPatientService;
import it.bitrock.demoluxottica.service.KeycloakService;
import it.bitrock.demoluxottica.service.PatientService;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
    private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);
    private final IGenericClient fhirClient;
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final KafkaPatientService kafkaService;
    private final KeycloakService keycloakService;

    public PatientServiceImpl(IGenericClient fhirClient, PatientRepository patientRepository, PatientMapper patientMapper, KafkaPatientService kafkaService, KeycloakService keycloakService) {
        this.fhirClient = fhirClient;
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.kafkaService = kafkaService;
        this.keycloakService = keycloakService;
    }

    public Patient add(@NonNull Patient patient) {
        log.info("Adding new patient: {}", patient);
        MethodOutcome outcome = fhirClient.create().resource(patient).execute();
        if (Boolean.TRUE.equals(outcome.getCreated())) {
            Patient savedPatient = (Patient) outcome.getResource();
            patientRepository.save(patientMapper.patientToPatientEntity(savedPatient));
            kafkaService.patientAdded(savedPatient);
            keycloakService.register(savedPatient);
            return savedPatient;
        } else {
            log.error("Can't create the new patient, outcome: {}", outcome);
            throw new PatientCreationException("Can't create the new patient");
        }
    }

    public List<Patient> findAll() {
        Bundle patients = fhirClient.search().forResource(Patient.class).returnBundle(Bundle.class).execute();
        return patients.getEntry().stream()
                .map(e -> (Patient) e.getResource())
                .toList();
    }

    public Patient findById(@NonNull String id) {
        return findByIdOptional(id).orElseThrow(NotFoundException::new);
    }

    public Patient update(@NonNull Patient patient) {
        return findByIdOptional(patient.getIdPart())
                .map(p -> {
                    MethodOutcome outcome = fhirClient.update().resource(patient).execute();
                    Patient updatedPatient = (Patient) outcome.getResource();
                    PatientEntity patientEntity = patientMapper.patientToPatientEntity(updatedPatient);
                    patientRepository.save(patientEntity);
                    kafkaService.patientUpdated(updatedPatient);
                    return updatedPatient;
                })
                .orElseThrow(NotFoundException::new);
    }

    public void delete(@NonNull String id) {
        fhirClient.delete().resourceById("Patient", id).execute();
        patientRepository.deleteById(id);
        kafkaService.patientRemoved(id);
        keycloakService.remove(id);
    }

    private Optional<Patient> findByIdOptional(String id) {
        try {
            return Optional.of(fhirClient.read().resource(Patient.class).withId(id).execute());
        } catch (ResourceNotFoundException | ResourceGoneException e) {
            return Optional.empty();
        }
    }
}
