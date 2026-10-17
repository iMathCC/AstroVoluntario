package com.astrovoluntario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoluntarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String telefone;
    private String cidade;
    private String estado;
    private Double latitude;
    private Double longitude;
    private String disponibilidade;
    private List<String> habilidades;
    private List<String> causas;
    private Double matchScore;
}
