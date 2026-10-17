package com.astrovoluntario.controller;

import com.astrovoluntario.dto.AuthRequestDTO;
import com.astrovoluntario.dto.AuthResponseDTO;
import com.astrovoluntario.dto.UserRequestDTO;
import com.astrovoluntario.dto.UserResponseDTO;
import com.astrovoluntario.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints de registro e login")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário", description = "Cria uma nova conta com nome, email e senha")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO userRequest) {
        UserResponseDTO newUser = userService.register(userRequest);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Autentica e retorna um token JWT")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequest) {
        AuthResponseDTO response = userService.login(authRequest);
        return ResponseEntity.ok(response);
    }
}