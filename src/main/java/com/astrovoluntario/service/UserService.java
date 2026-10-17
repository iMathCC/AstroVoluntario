package com.astrovoluntario.service;

import com.astrovoluntario.config.JwtService;
import com.astrovoluntario.dto.AuthRequestDTO;
import com.astrovoluntario.dto.AuthResponseDTO;
import com.astrovoluntario.dto.UserRequestDTO;
import com.astrovoluntario.dto.UserResponseDTO;
import com.astrovoluntario.entity.User;
import com.astrovoluntario.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO register(UserRequestDTO request) {
        //Verifica se email já existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado!");
        }

        //Cria o usuário com senha criptografada
        User user = User.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .role("VOLUNTARIO")
                .build();

        User usuarioSalvo = userRepository.save(user);

        return new UserResponseDTO(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail()
        );
    }

    public List<UserResponseDTO> listarTodos() {
        return userRepository.findAll().stream().map(user -> new UserResponseDTO(
                user.getId(),
                user.getNome(),
                user.getEmail()
        )).toList();
    }

    public AuthResponseDTO login(AuthRequestDTO authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getSenha()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Email ou senha inválidos.");
        }

        String token = jwtService.generateToken(authRequest.getEmail());

        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos."));

        return new AuthResponseDTO(token, user.getEmail(), user.getNome());
    }

    public UserResponseDTO registerAdmin(UserRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado!");
        }

        User user = User.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .role("ADMIN")
                .build();

        User usuarioSalvo = userRepository.save(user);

        return new UserResponseDTO(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail()
        );
    }
}