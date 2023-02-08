package it.bitrock.demoluxottica.service.impl;

import it.bitrock.demoluxottica.config.FhirContextSettings;
import it.bitrock.demoluxottica.service.FhirService;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class FhirServiceImpl implements FhirService {

    public <T extends IBaseResource> Stream<Bundle.BundleEntryComponent> getStreamOfAll(Class<T> resourceClass){
        Bundle bundle = (Bundle) FhirContextSettings.r4_client.search().forResource(resourceClass)
                .prettyPrint()
                .encodedJson()
                .preferResponseType(resourceClass)
                .execute();
        return bundle.getEntry().stream().
                toList().stream();
    }
}
