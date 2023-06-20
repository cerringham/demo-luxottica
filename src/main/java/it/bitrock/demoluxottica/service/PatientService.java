package it.bitrock.demoluxottica.service;

import org.hl7.fhir.r4.model.Patient;

import java.util.List;

public interface PatientService {
    Patient add(Patient patient);
    List<Patient> findAll();
    Patient findById(String id);
    Patient update(Patient patient);
    void delete(String id);
}
