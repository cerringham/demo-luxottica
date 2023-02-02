package it.bitrock.demoluxottica.config;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.IReadTyped;
import it.bitrock.demoluxottica.models.enumerations.FhirContextEnum;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FhirContextSettings {
    public static FhirContext fhirContext;
    public static IGenericClient client;

    public final static String CLIENT_URL = "https://hapi.fhir.org/baseR4";
    public FhirContextSettings(){
        FhirContextEnum context = FhirContextEnum.R4;
        switch (context){
            case R4 -> fhirContext = FhirContext.forR4();
            case R5 -> fhirContext = FhirContext.forR5();
        }
/*        if(clientURL != null){
            client = fhirContext.newRestfulGenericClient(clientURL);
        } else{
            client = fhirContext.newRestfulGenericClient(CLIENT_URL);
        }*/

        client = fhirContext.newRestfulGenericClient(CLIENT_URL);
    }

    public static IParser getParser(){
        return fhirContext.newJsonParser().setPrettyPrint(true);
    }

    public static String toString(IBaseResource iBaseResource){
        return FhirContextSettings.getParser().encodeResourceToString(iBaseResource);
    }

    public static <T extends IBaseResource> IReadTyped<T> getResource(Class<T> resourceClass){
        return (IReadTyped<T>) FhirContextSettings.client.read().resource(resourceClass);
    }
}
