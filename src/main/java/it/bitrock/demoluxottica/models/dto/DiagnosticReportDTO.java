package it.bitrock.demoluxottica.models.dto;

import it.bitrock.demoluxottica.service.FhirService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.springframework.beans.factory.annotation.Autowired;

@Entity(name="DiagnosticReport")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosticReportDTO {
    @Autowired
    FhirService fhirService;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition="text")
    private String content;

    public DiagnosticReportDTO(DiagnosticReport diagnosticReport){
        setContent(serialize(diagnosticReport));
    }
    public String serialize(DiagnosticReport diagnosticReport){
        return fhirService.toString(diagnosticReport);
    }
}
