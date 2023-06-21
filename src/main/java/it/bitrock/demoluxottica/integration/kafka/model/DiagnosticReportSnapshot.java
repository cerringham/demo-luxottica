package it.bitrock.demoluxottica.integration.kafka.model;

public class DiagnosticReportSnapshot {
    private String diagnosticReport;

    public DiagnosticReportSnapshot() {

    }

    public DiagnosticReportSnapshot(String diagnosticReport) {
        setDiagnosticReport(diagnosticReport);
    }

    public String getDiagnosticReport() {
        return diagnosticReport;
    }

    public void setDiagnosticReport(String diagnosticReport) {
        this.diagnosticReport = diagnosticReport;
    }
}


