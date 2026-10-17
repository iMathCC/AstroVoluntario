package com.astrovoluntario.repository;

import com.astrovoluntario.entity.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long>, JpaSpecificationExecutor<Voluntario> {

    Optional<Voluntario> findByUserId(Long userId);

    List<Voluntario> findByCidadeAndEstado(String cidade, String estado);

    List<Voluntario> findByCidadeIgnoreCase(String cidade);

    boolean existsByUserId(Long userId);
}