package it.bitrock.demoluxottica.utils;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Patient;

import java.io.IOException;
import java.io.Writer;

public class Utils {

    public static void main(String[] args) throws IOException {

        // Create a context
        FhirContext ctx = FhirContext.forR4();

        // Create a client
        IGenericClient client = ctx.newRestfulGenericClient("https://hapi.fhir.org/baseR4");

        // Read a patient with the given ID
        Patient patient = client.read().resource(Patient.class).withId("example").execute();

        // Print the output
        String string = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient);
        //ctx.newJsonParser().setPrettyPrint(true).encodeResourceToWriter(patient, Writer.nullWriter());
        System.out.println(string);
    }


}
