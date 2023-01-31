package it.bitrock.demoluxottica.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import it.bitrock.demoluxottica.model.Role;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegistrazioneDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(hidden = true)
    String id = "uno";
    @NotNull @NotBlank @Email
    String username;
    @NotNull @NotBlank
    String nome;
    @NotNull @NotBlank
    String cognome;
    @NotNull @NotBlank
    String email;
    @Schema(hidden = true)
    Role Ruolo;
//    LocalDate dataInizio;
//    LocalDate dateFine;

}
