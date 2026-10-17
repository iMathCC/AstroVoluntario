package com.astrovoluntario.mapper;

import com.astrovoluntario.dto.VoluntarioRequestDTO;
import com.astrovoluntario.dto.VoluntarioResponseDTO;
import com.astrovoluntario.entity.Causa;
import com.astrovoluntario.entity.Habilidade;
import com.astrovoluntario.entity.Voluntario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VoluntarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "habilidades", ignore = true)
    @Mapping(target = "causas", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Voluntario toEntity(VoluntarioRequestDTO requestDTO);

    @Mapping(target = "nome", source = "user.nome")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "habilidades", source = "habilidades", qualifiedByName = "habilidadesToNomes")
    @Mapping(target = "causas", source = "causas", qualifiedByName = "causasToNomes")
    VoluntarioResponseDTO toResponseDTO(Voluntario voluntario);

    @Named("habilidadesToNomes")
    default List<String> habilidadesToNomes(List<Habilidade> habilidades) {
        if (habilidades == null) return List.of();
        return habilidades.stream()
                .map(Habilidade::getNome)
                .collect(Collectors.toList());
    }

    @Named("causasToNomes")
    default List<String> causasToNomes(List<Causa> causas) {
        if (causas == null) return List.of();
        return causas.stream()
                .map(Causa::getNome)
                .collect(Collectors.toList());
    }
}