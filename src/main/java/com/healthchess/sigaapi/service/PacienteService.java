package com.healthchess.sigaapi.service;

import com.healthchess.sigaapi.model.Paciente;
import com.healthchess.sigaapi.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

//Service tabela paciente
@Service
public class PacienteService {

    //Injeção de dependência
    @Autowired
    private PacienteRepository repository;

    //Método para buscar todos os pacientes
    public List<Paciente> bucarTodos(){
        return repository.findAll();
    }

    //Método para buscar paciente por id
    public Optional<Paciente> buscar(Integer id){
        return repository.findById(id);
    }

    //Método para excluir paciente por id
    public void excluir(Integer id){
        repository.deleteById(id);
    }

    //Método para criar um novo paciente.
    public Paciente salvar(@Valid Paciente paciente){
        return repository.save(paciente);
    }

    //Método para atualizar dados do paciente
    public Paciente atualizar(@Valid Paciente paciente){
        Paciente pacienteAtualizar = repository.findById(paciente.getId()).get();
        pacienteAtualizar.setNome(paciente.getNome());
        pacienteAtualizar.setDataNascimento(paciente.getDataNascimento());
        pacienteAtualizar.setCpf(paciente.getCpf());
        return repository.save(pacienteAtualizar);
    }

}
