package it.bitrock.demoluxottica.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CodingDTO(String system, String code, String display) {
}
