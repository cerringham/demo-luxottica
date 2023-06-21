package it.bitrock.demoluxottica.mapper;


import java.util.Date;
import java.util.Optional;

import it.bitrock.demoluxottica.integration.kafka.model.DiagnosticReportSnapshot;
import it.bitrock.demoluxottica.models.dto.DiagnosticReportDTO;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.IdType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Mapper
public interface DiagnosticReportMapper {

    DiagnosticReportMapper INSTANCE = Mappers.getMapper(DiagnosticReportMapper.class);

    @Mapping(target = "id", source = "report.id", qualifiedByName = "mapId")
    @Mapping(target = "effectiveDateTime", source = "report.effectiveDateTimeType", qualifiedByName = "mapDate")
    DiagnosticReportDTO mapDiagnosticReport(DiagnosticReport report);

    @Named("mapId")
    default String mapIdToUnqualified(String id) {
        return Optional.of(id)
                .map(IdType::new)
                .map(IdType::toUnqualified)
                .map(IdType::getIdPart)
                .orElse(null);
    }

    @Named("mapDate")
    default Date mapDate(DateTimeType input) {
        return input.getValue();
    }

    @Mapping(target = "diagnosticReport", source = "dr")
    DiagnosticReportSnapshot fromFhirToDiagnosticReportSnapshot(String dr);

}
