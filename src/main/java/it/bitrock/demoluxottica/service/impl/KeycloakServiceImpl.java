package it.bitrock.demoluxottica.service.impl;

import it.bitrock.demoluxottica.mapper.PatientMapper;
import it.bitrock.demoluxottica.service.KeycloakService;
import org.hl7.fhir.r4.model.Patient;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

@Service
public class KeycloakServiceImpl implements KeycloakService {
    private final Keycloak keycloak;
    private final PatientMapper patientMapper;

    public KeycloakServiceImpl(Keycloak keycloak, PatientMapper patientMapper) {
        this.keycloak = keycloak;
        this.patientMapper = patientMapper;
    }

    @Override
    public UserRepresentation register(Patient patient) {
        UserRepresentation userRepresentation = patientMapper.patientToUserRepresentation(patient);
        userRepresentation.setEnabled(true);
        Response response = keycloak.realm("luxottica-spike").users().create(userRepresentation);
        response.close();
        return userRepresentation;
    }

    @Override
    public void remove(String id) {
        Response response = keycloak.realm("luxottica-spike").users().delete(id);
        response.close();
    }
}
