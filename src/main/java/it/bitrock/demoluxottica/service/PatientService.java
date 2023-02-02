package it.bitrock.demoluxottica.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import it.bitrock.demoluxottica.config.FhirContextSettings;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PatientService {

    public String getPatientByStringId(String id) {
        if(id == null || id.isEmpty()) {
            return "id cannot be blank or null";
        }
        Patient patient = FhirContextSettings.getResource(Patient.class).withId(id).execute();
        // patient.
       return FhirContextSettings.toString(patient);
    }
}
