package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DISCIPLINAS")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disciplina_id")
    private Integer id;

    @NotBlank(message = "Nome não deve ser vazio")
    @Column(name = "disciplina_name", nullable = false)
    private String name;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titular")
    private Professor titular;

    @PrePersist
    public void prePersist() {
        setDateTime(LocalDateTime.now());
    }

    // Parece que o builder não pegou o construtor com esses argumentos
    public Disciplina(Integer id2, String name2, Professor professor2,
            LocalDateTime datetime2) {
    }

}