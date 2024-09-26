package com.buenodev.reportstest.entities;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Aluno {
    private String nome;
    private String curso;
    private Integer cargaHoraria;
    private Date dataInicioCurso;
    private Date dataTerminoCurso;

}
