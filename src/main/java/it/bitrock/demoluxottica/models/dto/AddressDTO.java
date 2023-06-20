package it.bitrock.demoluxottica.models.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AddressDTO(
        String country,
        String district,
        String city,
        String street,
        String number
) {
}
