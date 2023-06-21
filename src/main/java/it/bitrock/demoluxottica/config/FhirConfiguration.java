package it.bitrock.demoluxottica.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FhirConfiguration {

    @Bean
    public FhirContext fhirContext() {
        return FhirContext.forR4();
    }
    @Bean
    public IGenericClient getClient(@Value("${FHIR_HOST}") String url, FhirContext context) {
        return context.newRestfulGenericClient(url);
    }
}
