package com.healthchess.sigaapi.service;

import com.healthchess.sigaapi.model.Paciente;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface PacienteService {

    public List<Paciente> bucarTodos();
    public Optional<Paciente> buscar(Integer id);
    public void excluir(Integer id);
    public Paciente salvar(@Valid Paciente paciente);
    public Paciente atualizar(@Valid Paciente paciente);

}
