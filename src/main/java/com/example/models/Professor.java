package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "professor_name", nullable = false)
    private String name;

    // @Column(sexo = "professor_sexo", nullable = false)
    // private enum sexo{
    //    MASCULINO, FEMININO
    // };

    // @Column(disciplina = "professor_disciplina", nullable = false)
    // private String disciplina;
}