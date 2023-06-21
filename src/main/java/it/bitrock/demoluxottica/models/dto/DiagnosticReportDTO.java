package it.bitrock.demoluxottica.models.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DiagnosticReportDTO(String resourceType, String id, List<ReferenceDTO> basedOn, String status, CodeDTO code,
                                  ReferenceDTO subject, ReferenceDTO encounter,
                                  Date effectiveDateTime, Date issued, List<ReferenceDTO> performer,
                                  List<ReferenceDTO> result) {
}
