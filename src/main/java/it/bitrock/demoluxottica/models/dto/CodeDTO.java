package it.bitrock.demoluxottica.models.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CodeDTO(List<CodingDTO> coding, String text) {
}
