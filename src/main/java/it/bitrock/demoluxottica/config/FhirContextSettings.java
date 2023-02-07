package it.bitrock.demoluxottica.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.IReadTyped;
import it.bitrock.demoluxottica.models.enumerations.FhirContextEnum;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class FhirContextSettings {
    public static FhirContext fhirContext;
    public static IGenericClient r4_client;
    public static IGenericClient r5_client;

    public final static String R4_CLIENT_URL = "https://hapi.fhir.org/baseR4";
    public final static String R5_CLIENT_URL = "https://hapi.fhir.org/baseR5";
    public FhirContextSettings(){
        FhirContextEnum context = FhirContextEnum.R4;
        switch (context){
            case R4 -> fhirContext = FhirContext.forR4();
            case R5 -> fhirContext = FhirContext.forR5();
        }
        r4_client = fhirContext.newRestfulGenericClient(R4_CLIENT_URL);
        r5_client = fhirContext.newRestfulGenericClient(R5_CLIENT_URL);
    }

    public static IParser getParser(){
        return fhirContext.newJsonParser().setPrettyPrint(true);
    }

    public static String toString(IBaseResource iBaseResource){
        return FhirContextSettings.getParser().encodeResourceToString(iBaseResource);
    }
    public static <T extends IBaseResource> String optionalToString(Class<T> resourceClass, Optional<T> iBaseResource){
        return FhirContextSettings.getParser().encodeResourceToString((resourceClass) iBaseResource.get());
    }

    public static <T extends IBaseResource> IReadTyped<T> getResource(Class<T> resourceClass, FhirContextEnum fhirContextEnum){
        return (IReadTyped<T>) FhirContextSettings.r4_client.read().resource(resourceClass);
    }
}
