package it.bitrock.demoluxottica.service;

import org.hl7.fhir.r4.model.Encounter;

import java.util.Optional;

public interface EncounterService {
    Optional<Encounter> getEncounterById(String encounterId);
}
