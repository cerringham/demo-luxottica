package it.bitrock.demoluxottica.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReferenceDTO(String reference, String display) {
}
