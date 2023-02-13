package it.bitrock.demoluxottica.models.dto;

import it.bitrock.demoluxottica.service.FhirService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.springframework.beans.factory.annotation.Autowired;


@Entity(name = "DiagnosticReport")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosticReportDTO {
    @Autowired
    @Transient
    FhirService fhirService;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition = "text")
    private String content;

    public DiagnosticReportDTO(DiagnosticReport diagnosticReport) {
        setContent(contentToString(diagnosticReport));
    }

    public String contentToString(DiagnosticReport diagnosticReport) {
        return fhirService.toString(diagnosticReport);
    }

    public void serialize() {
 /*       // Create a FHIR context
        FhirContext ctx = FhirContext.forR4();
// The following example is a simple serialized Patient resource to parse
        String input = "{" +
                "\"resourceType\" : \"Patient\"," +
                "  \"name\" : [{" +
                "    \"family\": \"Simpson\"" +
                "  }]" +
                "}";

// Instantiate a new parser
        IParser parser = ctx.newJsonParser();

// Parse it
        Patient parsed = parser.parseResource(Patient.class, input);
        System.out.println(parsed.getName().get(0).getFamily());*/
    }
}
