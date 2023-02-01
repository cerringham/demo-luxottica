package it.bitrock.demoluxottica.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//Usando "User" il programma va in errore, probabilmente per qualche conflitto con altre librerie
public class Utente {

    static final String DATE_PATTERN = "dd/MM/yyyy";
    static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(value = AccessLevel.NONE)
    private String id;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role ruolo;
//    Sede sede;
//    List<MultipartFile> documenti;
//    Organizzazione organizzazione;
//    List<Servizio> servizi;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime inizioSessione;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime fineSessione;

}
