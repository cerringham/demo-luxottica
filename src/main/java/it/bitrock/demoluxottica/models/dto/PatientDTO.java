package it.bitrock.demoluxottica.models.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PatientDTO(
        String id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String phoneNumber,
        String email,
        AddressDTO address
) {
}
