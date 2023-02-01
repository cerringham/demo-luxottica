package it.bitrock.demoluxottica.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegistrazioneDTO {

    static final String DATE_PATTERN = "dd/MM/yyyy";
    static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";

    //FIXME Non auto-genera un ID
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(hidden = true)
//    private String id;
    private String id = "1@2#3@4#5@6#7";
    @NotNull @NotBlank @Email
    private String username;
    @NotNull @NotBlank
    private String nome;
    @NotNull @NotBlank
    private String cognome;
    @NotNull @NotBlank
    private String email;
    private String password;
    @Schema(hidden = true)
    private Role Ruolo;
    @JsonFormat(pattern = DATE_TIME_PATTERN) @Schema(hidden = true)
    LocalDateTime inizioSessione = LocalDateTime.now();
    @JsonFormat(pattern = DATE_PATTERN)
    LocalDateTime fineSessione = LocalDateTime.of(2023, 10, 11, 00, 00);


}
