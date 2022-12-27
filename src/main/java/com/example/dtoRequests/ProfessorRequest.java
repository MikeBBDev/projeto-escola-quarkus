package com.example.dtoRequests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class ProfessorRequest {

    @NotBlank(message = "Nome deve ser não nulo")
    @Size(min = 3, message = "Tamanho mínimo do nome é de 3 caracteres")
    private String name;

}