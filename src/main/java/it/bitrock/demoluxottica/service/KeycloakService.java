package it.bitrock.demoluxottica.service;

import org.hl7.fhir.r4.model.Patient;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakService {
    UserRepresentation register(Patient patient);
    void remove(String id);
}
