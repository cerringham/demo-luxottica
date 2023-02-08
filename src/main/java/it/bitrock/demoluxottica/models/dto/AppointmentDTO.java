package it.bitrock.demoluxottica.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDTO {

    static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";

    @Schema(hidden = true)
    private String id = String.valueOf(UUID.randomUUID());
    private String description;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime start;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime end;
    @Schema(hidden = true)
    private String patientID = "Patient/example";
    private List<String> participant;

}
