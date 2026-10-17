package com.astrovoluntario.service;

import com.astrovoluntario.dto.VoluntarioRequestDTO;
import com.astrovoluntario.dto.VoluntarioResponseDTO;
import com.astrovoluntario.entity.Causa;
import com.astrovoluntario.entity.Habilidade;
import com.astrovoluntario.entity.User;
import com.astrovoluntario.entity.Voluntario;
import com.astrovoluntario.mapper.VoluntarioMapper;
import com.astrovoluntario.repository.CausaRepository;
import com.astrovoluntario.repository.HabilidadeRepository;
import com.astrovoluntario.repository.UserRepository;
import com.astrovoluntario.repository.VoluntarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;
    private final UserRepository userRepository;
    private final HabilidadeRepository habilidadeRepository;
    private final CausaRepository causaRepository;
    private final VoluntarioMapper voluntarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public VoluntarioResponseDTO criarPerfil(VoluntarioRequestDTO request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (voluntarioRepository.existsByUserId(user.getId())) {
            throw new RuntimeException("Usuário já possui perfil de voluntário");
        }

        if (!user.getRole().equals("VOLUNTARIO")) {
            throw new RuntimeException("Usuário não tem permissão para criar perfil de voluntário");
        }

        user.setNome(request.getNome());
        user.setEmail(request.getEmail());

        if (request.getSenha() != null && !request.getSenha().isEmpty()) {
            user.setSenha(passwordEncoder.encode(request.getSenha()));
        }
        userRepository.save(user);

        Voluntario voluntario = voluntarioMapper.toEntity(request);
        voluntario.setUser(user);

        if (request.getHabilidades() != null && !request.getHabilidades().isEmpty()) {
            List<Habilidade> habilidades = habilidadeRepository.findAllById(request.getHabilidades());
            voluntario.setHabilidades(habilidades);
        }

        if (request.getCausas() != null && !request.getCausas().isEmpty()) {
            List<Causa> causas = causaRepository.findAllById(request.getCausas());
            voluntario.setCausas(causas);
        }

        Voluntario saved = voluntarioRepository.save(voluntario);
        log.info("Perfil de voluntário criado para usuário: {}", email);

        return voluntarioMapper.toResponseDTO(saved);
    }

    public VoluntarioResponseDTO meuPerfil() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Voluntario voluntario = voluntarioRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Perfil de voluntário não encontrado"));

        return voluntarioMapper.toResponseDTO(voluntario);
    }

    public List<VoluntarioResponseDTO> buscarTodos() {
        return voluntarioRepository.findAll().stream()
                .map(voluntarioMapper::toResponseDTO)
                .toList();
    }
}
