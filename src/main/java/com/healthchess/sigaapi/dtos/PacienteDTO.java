package com.healthchess.sigaapi.dtos;

import com.healthchess.sigaapi.model.Paciente;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.sql.Date;

@Getter @Setter
public class PacienteDTO {
    private Integer id;
    private String nome;
    private Date dataNascimento;
    @CPF
    private String cpf;

    public PacienteDTO() {
    }

    public PacienteDTO(Paciente obj) {
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.dataNascimento = obj.getDataNascimento();
        this.cpf = obj.getCpf();
    }
}
