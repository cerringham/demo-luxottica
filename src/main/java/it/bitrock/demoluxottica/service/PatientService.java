package it.bitrock.demoluxottica.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PatientService {

    public String getPatientByStringId(String id) {
        if(id == null || id.isEmpty()) {
            return "id cannot be blank or null";
        }
        // Create a context
        FhirContext ctx = FhirContext.forR4();

        // Create a client
        IGenericClient client = ctx.newRestfulGenericClient("https://hapi.fhir.org/baseR4");

        // Read a patient with the given ID
        Patient patient = client.read().resource(Patient.class).withId(id).execute();

       return ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient);
    }
}
