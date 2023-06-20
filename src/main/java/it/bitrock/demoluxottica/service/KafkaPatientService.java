package it.bitrock.demoluxottica.service;

import org.hl7.fhir.r4.model.Patient;

public interface KafkaPatientService {
    void patientAdded(Patient patient);
    void patientUpdated(Patient patient);
    void patientRemoved(String id);
}
