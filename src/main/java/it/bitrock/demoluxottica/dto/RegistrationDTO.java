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

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegistrationDTO {

    static final String DATE_PATTERN = "dd/MM/yyyy";
    static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";

    //FIXME Non auto-genera un ID
    @Schema(hidden = true)
    private UUID id;
    @NotNull @NotBlank @Email
    private String username;
    @NotNull @NotBlank
    private String name;
    @NotNull @NotBlank
    private String surname;
    @NotNull @NotBlank
    private String email;
    private String password;
    @Schema(hidden = true)
    private Role role;
    @JsonFormat(pattern = DATE_TIME_PATTERN) @Schema(hidden = true)
    LocalDateTime startSession = LocalDateTime.now();
    @JsonFormat(pattern = DATE_PATTERN)
    LocalDateTime endSession = LocalDateTime.of(2023, 10, 11, 00, 00);

    {
        this.id = UUID.randomUUID();
    }

}
