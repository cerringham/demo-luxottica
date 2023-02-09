package it.bitrock.demoluxottica.repository;

import it.bitrock.demoluxottica.models.dto.DiagnosticReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosticReportRepository<T> extends JpaRepository<DiagnosticReportDTO, T> {

}