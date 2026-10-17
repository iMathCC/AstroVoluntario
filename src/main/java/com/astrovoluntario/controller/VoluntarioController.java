package com.astrovoluntario.controller;

import com.astrovoluntario.dto.VoluntarioRequestDTO;
import com.astrovoluntario.dto.VoluntarioResponseDTO;
import com.astrovoluntario.service.VoluntarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voluntarios")
@RequiredArgsConstructor
@Tag(name = "Voluntários", description = "Endpoints para gestão de perfis de voluntários")
public class VoluntarioController {

    private final VoluntarioService voluntarioService;

    @PostMapping("/me/perfil")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    @Operation(summary = "Criar perfil de voluntário", description = "Cria o perfil completo do voluntário para o usuário logado")
    public ResponseEntity<VoluntarioResponseDTO> criarPerfil(@Valid @RequestBody VoluntarioRequestDTO request) {
        VoluntarioResponseDTO response = voluntarioService.criarPerfil(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('VOLUNTARIO')")
    @Operation(summary = "Meu perfil", description = "Retorna o perfil do voluntário logado")
    public ResponseEntity<VoluntarioResponseDTO> meuPerfil() {
        VoluntarioResponseDTO response = voluntarioService.meuPerfil();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ONG')")
    @Operation(summary = "Listar voluntários", description = "Retorna todos os perfis de voluntários (apenas ADMIN/ONG)")
    public ResponseEntity<List<VoluntarioResponseDTO>> listarTodos() {
        List<VoluntarioResponseDTO> voluntarios = voluntarioService.buscarTodos();
        return ResponseEntity.ok(voluntarios);
    }
}