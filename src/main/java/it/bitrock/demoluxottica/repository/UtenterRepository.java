package it.bitrock.demoluxottica.repository;

import it.bitrock.demoluxottica.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenterRepository extends JpaRepository<Utente, Long> {

}
