package com.astrovoluntario.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "causas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Causa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;
}
