package it.bitrock.demoluxottica.service;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;

import java.util.stream.Stream;

public interface FhirService {

    public <T extends IBaseResource> Stream<Bundle.BundleEntryComponent> getStreamOfAll(Class<T> resourceClass);

    public String toString(IBaseResource iBaseResource);
}
