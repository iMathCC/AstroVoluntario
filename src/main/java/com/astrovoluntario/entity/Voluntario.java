package com.astrovoluntario.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "voluntarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voluntario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(length = 20)
    private String telefone;

    @Column(length = 100)
    private String cidade;

    @Column(length = 2)
    private String estado;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column(length = 20)
    private String disponibilidade;

    @ManyToMany
    @JoinTable(
            name = "voluntario_habilidades",
            joinColumns = @JoinColumn(name = "voluntario_id"),
            inverseJoinColumns = @JoinColumn(name = "habilidade_id")
    )
    @Builder.Default
    private List<Habilidade> habilidades = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "voluntario_causas",
            joinColumns = @JoinColumn(name = "voluntario_id"),
            inverseJoinColumns = @JoinColumn(name = "causa_id")
    )
    @Builder.Default
    private List<Causa> causas = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}