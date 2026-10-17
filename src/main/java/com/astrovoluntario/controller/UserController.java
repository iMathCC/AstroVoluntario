package com.astrovoluntario.controller;

import com.astrovoluntario.dto.UserRequestDTO;
import com.astrovoluntario.dto.UserResponseDTO;
import com.astrovoluntario.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Endpoints de gerenciamento de usuários")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos os usuários", description = "Retorna a lista completa de usuários cadastrados")
    public ResponseEntity<List<UserResponseDTO>> listarUsuarios() {
        List<UserResponseDTO> usuarios = userService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar novo usuário", description = "Cadastra um novo usuário no sistema")
    public ResponseEntity<UserResponseDTO> criarUsuario(@Valid @RequestBody UserRequestDTO request){
        UserResponseDTO response = userService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registrar ADMIN", description = "Apenas ADMIN pode criar outro ADMIN")
    public ResponseEntity<UserResponseDTO> registerAdmin(@Valid @RequestBody UserRequestDTO userRequest) {
        UserResponseDTO newUser = userService.registerAdmin(userRequest);
        return ResponseEntity.ok(newUser);
    }
}