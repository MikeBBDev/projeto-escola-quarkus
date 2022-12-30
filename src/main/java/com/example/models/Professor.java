package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PROFESSORES")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id")
    private Integer id;

    @NotBlank(message = "Nome não deve ser vazio")
    @Size(min = 3, message = "Nome com um mínimo de 3 letras")
    @Column(name = "professor_name", nullable = false)
    private String name;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dateTime;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "titular")
    private Disciplina disciplina;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tutor")
    private List<Aluno> alunos;

    @PrePersist
    public void prePersist() {
        setDateTime(LocalDateTime.now());
    }

    // Parece que o builder não pegou o construtor com esses argumentos
    public Professor(Integer id2, String name2, Disciplina disciplina2, List<Aluno> tutorados,
            LocalDateTime datetime2) {
    }
}