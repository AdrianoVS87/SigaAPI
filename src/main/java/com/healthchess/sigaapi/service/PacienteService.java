package com.healthchess.sigaapi.service;

import com.healthchess.sigaapi.model.Paciente;
import com.healthchess.sigaapi.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    public List<Paciente> bucarTodos(){
        return repository.findAll();
    }

    public Paciente buscar(Integer id){
        return repository.findById(id).get();
    }

    public void excluir(Integer id){
        repository.deleteById(id);
    }

    public Paciente salvar(Paciente paciente){
        return repository.save(paciente);
    }

    public Paciente atualizar(Paciente paciente){
        Paciente pacienteAtualizar = repository.findById(paciente.getId()).get();
        pacienteAtualizar.setNome(paciente.getNome());
        pacienteAtualizar.setDataNascimento(paciente.getDataNascimento());
        return repository.save(pacienteAtualizar);
    }

}
