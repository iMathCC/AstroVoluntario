package com.astrovoluntario.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoluntarioRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String senha;

    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    @Pattern(regexp = "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}", message = "Telefone inválido")
    private String telefone;

    private String cidade;

    @Pattern(regexp = "[A-Z]{2}", message = "Estado deve ter 2 letras maiúsculas")
    private String estado;

    private Double latitude;
    private Double longitude;

    private String disponibilidade;

    private List<Long> habilidades;
    private List<Long> causas;
}