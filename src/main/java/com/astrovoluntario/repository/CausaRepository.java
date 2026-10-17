package com.astrovoluntario.repository;

import com.astrovoluntario.entity.Causa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CausaRepository extends JpaRepository<Causa, Long> {

    Optional<Causa> findByNome(String nome);

    boolean existsByNome(String nome);
}