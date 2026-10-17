package com.astrovoluntario.service;

import com.astrovoluntario.dto.UserRequestDTO;
import com.astrovoluntario.dto.UserResponseDTO;
import com.astrovoluntario.entity.User;
import com.astrovoluntario.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRequestDTO userRequest;
    private User user;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequestDTO();
        userRequest.setNome("João Teste");
        userRequest.setEmail("joao@teste.com");
        userRequest.setSenha("123456");

        user = User.builder()
                .id(1L)
                .nome("João Teste")
                .email("joao@teste.com")
                .senha("senha_criptografada")
                .role("VOLUNTARIO")
                .build();
    }

    @Test
    void deveCriarUsuarioComSucesso() {
        // given
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("senha_criptografada");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // when
        UserResponseDTO response = userService.register(userRequest);

        // then
        assertNotNull(response);
        assertEquals("João Teste", response.getNome());
        assertEquals("joao@teste.com", response.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExiste() {
        // given
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.register(userRequest);
        });

        assertEquals("Email já cadastrado!", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}