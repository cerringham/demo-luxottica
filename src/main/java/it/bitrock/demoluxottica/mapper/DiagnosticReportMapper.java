package it.bitrock.demoluxottica.mapper;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import it.bitrock.demoluxottica.models.avro.DiagnosticReportRecord;
import it.bitrock.demoluxottica.models.dto.DiagnosticReportDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.hl7.fhir.r4.model.*;
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

    @Mapping(target = "performers", source = "dr.performer")
    @Mapping(target = "effectiveDateTime", source = "dr.effectiveDateTimeType")
    DiagnosticReportRecord fromDiagnosticReportToDiagnosticReportRecord(DiagnosticReport dr);

    default String mapReferencesToString(List<Reference> list) {
        return Optional.ofNullable(list)
                .filter(CollectionUtils::isNotEmpty)
                .map(l -> {
                    StringBuilder output = new StringBuilder();
                    l.forEach(r -> output.append(r.getReference()));
                    return output.toString();
                })
                .orElse(null);
    }

    default long mapDateTimeTypeToLong(DateTimeType date) {
        return Optional.of(date)
                .map(d -> d.getValue().toInstant().toEpochMilli())
                .orElse(null);
    }

    default String mapReferenceToString(Reference ref) {
        return Optional.ofNullable(ref)
                .map(Reference::toString)
                .orElse(null);
    }

    default String mapCodeableConceptToString(CodeableConcept code) {
        return Optional.ofNullable(code)
                .map(CodeableConcept::toString)
                .orElse(null);
    }

    default String mapStatusToString(DiagnosticReport.DiagnosticReportStatus status) {
        return Optional.ofNullable(status)
                .map(DiagnosticReport.DiagnosticReportStatus::toString)
                .orElse(null);
    }
}
