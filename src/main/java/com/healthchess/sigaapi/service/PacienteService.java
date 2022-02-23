package com.healthchess.sigaapi.service;

import com.healthchess.sigaapi.dtos.PacienteDTO;

import javax.validation.Valid;
import java.util.List;

public interface PacienteService {

    public List<PacienteDTO> bucarTodos();
    public PacienteDTO buscar(Integer id);
    public void excluir(Integer id);
    public PacienteDTO salvar(@Valid PacienteDTO paciente);
    public PacienteDTO atualizar(Integer id, @Valid PacienteDTO paciente);

}
