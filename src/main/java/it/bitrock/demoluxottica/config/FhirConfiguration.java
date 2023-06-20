package it.bitrock.demoluxottica.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FhirConfiguration {
    @Value("${fhir.host}")
    private String host;

    @Bean
    public IGenericClient fhirClient() {
        FhirContext ctx = FhirContext.forR4();
        return ctx.newRestfulGenericClient(host);
    }
}
