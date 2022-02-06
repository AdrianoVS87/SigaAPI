package com.healthchess.sigaapi.service;

import com.healthchess.sigaapi.model.Paciente;
import com.healthchess.sigaapi.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Paciente buscar(Integer id){
        return repository.findById(id).get();
    }

    //Método para excluir paciente por id
    public void excluir(Integer id){
        repository.deleteById(id);
    }

    //Método para criar um novo paciente.
    public Paciente salvar(Paciente paciente){
        return repository.save(paciente);
    }

    //Método para atualizar dados do paciente
    public Paciente atualizar(Paciente paciente){
        Paciente pacienteAtualizar = repository.findById(paciente.getId()).get();
        pacienteAtualizar.setNome(paciente.getNome());
        pacienteAtualizar.setDataNascimento(paciente.getDataNascimento());
        pacienteAtualizar.setCpf(paciente.getCpf());
        return repository.save(pacienteAtualizar);
    }

}
