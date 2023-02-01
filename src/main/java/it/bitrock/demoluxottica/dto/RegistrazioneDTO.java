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

    //FIXME Non auto-genera un ID
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(hidden = true)
    private String id;
//    private String id = "uno";
    @NotNull @NotBlank @Email
    private String username;
    @NotNull @NotBlank
    private String nome;
    @NotNull @NotBlank
    private String cognome;
    @NotNull @NotBlank
    private String email;
    @Schema(hidden = true)
    private Role Ruolo;
//    LocalDate dataInizio;
//    LocalDate dateFine;

}
