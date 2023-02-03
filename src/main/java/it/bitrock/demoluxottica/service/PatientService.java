package it.bitrock.demoluxottica.service;

import it.bitrock.demoluxottica.config.FhirContextSettings;
import it.bitrock.demoluxottica.models.enumerations.FhirContextEnum;
import it.bitrock.demoluxottica.utils.FhirUtils;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PatientService {

    public List<Patient> getAllPatient(){
        return FhirUtils.getStreamOfAll(Patient.class)
                .map(bundleEntryComponent -> (Patient) bundleEntryComponent.getResource())
                .collect(Collectors.toList());
    }

    public String getPatientByStringId(String id, FhirContextEnum fhirContext) {
        if(id == null || id.isEmpty()) {
            return "id cannot be blank or null";
        }
        Patient patient = FhirContextSettings.getResource(Patient.class, fhirContext).withId(id).execute();
       return FhirContextSettings.toString(patient);
    }

}
