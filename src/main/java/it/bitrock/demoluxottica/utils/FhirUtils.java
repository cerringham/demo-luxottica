package it.bitrock.demoluxottica.utils;

import it.bitrock.demoluxottica.config.FhirContextSettings;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.DomainResource;
import org.hl7.fhir.r4.model.Resource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FhirUtils {


    public FhirUtils(){

    }

    public static <T extends IBaseResource> Stream<Bundle.BundleEntryComponent> getStreamOfAll(Class<T> resourceClass){
        Bundle bundle = (Bundle) FhirContextSettings.r4_client.search().forResource(resourceClass)
                .prettyPrint()
                .encodedJson()
                .preferResponseType(resourceClass)
                .execute();
        return bundle.getEntry().stream().
                toList().stream();
    }
}
