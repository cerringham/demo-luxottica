package it.bitrock.demoluxottica.repository;

import it.bitrock.demoluxottica.models.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity, String> {
}
