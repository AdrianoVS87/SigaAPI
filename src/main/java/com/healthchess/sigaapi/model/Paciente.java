package com.healthchess.sigaapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;

//Modelo tabela paciente
@Entity
@Table(name="tb_paciente")
@Getter @Setter
public class Paciente {

    //Campo de índice da tabela paciente, auto incremento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //Campo nome da tabela paciente, não aceitando valores nulos, vazios ou com mais de 60 caractéres
    @Size(max=60)
    @NotEmpty
    @NotNull
    private String nome;

    //Campo data de nascimento da tabela paciente
    private Date dataNascimento;

    //Método construtor de paciente
    public Paciente(Integer id, String nome, Date dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    //Método construtor vazio de paciente
    public Paciente(){

    }
    //Método toString() de paciente
    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                '}';
    }
}
