package it.bitrock.demoluxottica.service;

import org.hl7.fhir.r4.model.*;

import java.util.List;
import java.util.Optional;

public interface DiagnosticReportService {
    public Optional<List<DiagnosticReport>> getAllDiagnosticReports();
    public Optional<DiagnosticReport> getDiagnosticReportById(String id);
    public Optional<Patient> getPatientByDiagnosticReportId(String id);
    public Optional<Encounter> getEncounterByDiagnosticReportId(String id);
    public Optional<List<Reference>>  getPerformerByDiagnosticReportId(String id);
    public Optional<List<Reference>> getResultByDiagnosticReportId(String id);
}
