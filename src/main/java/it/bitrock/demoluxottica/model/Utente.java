package it.bitrock.demoluxottica.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Long id;
    private String nome_utente;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role ruolo;
//    Sede sede;
//    List<MultipartFile> documenti;
//    Organizzazione organizzazione;
//    List<Servizio> servizi;
    private LocalDateTime inizioSessione;
    private LocalDateTime fineSessione;

}
