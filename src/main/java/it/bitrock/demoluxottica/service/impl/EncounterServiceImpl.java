package it.bitrock.demoluxottica.service.impl;

import it.bitrock.demoluxottica.config.FhirContextSettings;
import it.bitrock.demoluxottica.models.enumerations.FhirContextEnum;
import it.bitrock.demoluxottica.service.EncounterService;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.Encounter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class EncounterServiceImpl implements EncounterService {
    public Optional<Encounter> getEncounterById(String id) {
        if(id == null || id.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(FhirContextSettings.getResource(Encounter.class, FhirContextEnum.R4).withId(id).execute());
    }
}
