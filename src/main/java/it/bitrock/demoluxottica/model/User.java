package it.bitrock.demoluxottica.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    String nome_utente;
    String password;
    Role ruolo;
//    Sede sede;
//    List<MultipartFile> documenti;
//    Organizzazione organizzazione;
//    List<Servizio> servizi;
    LocalDateTime inizioSessione;
    LocalDateTime fineSessione;

}
