package it.bitrock.demoluxottica.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDTO {

    static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";

    @Schema(hidden = true)
    private String id = String.valueOf(UUID.randomUUID());
    @Schema(description = "Surname")
    private String familyName;
    @Schema(description = "Name")
    private String givenName;
    private String email;
    private String telephone;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime start;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime end;

    //TODO ADD PARAMETER

}
