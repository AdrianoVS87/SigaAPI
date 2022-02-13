package com.healthchess.sigaapi.service.impl;

import com.healthchess.sigaapi.model.Paciente;
import com.healthchess.sigaapi.repository.PacienteRepository;
import com.healthchess.sigaapi.service.PacienteService;
import com.healthchess.sigaapi.service.exceptions.DataIntegrityViolationException;


import com.healthchess.sigaapi.service.exceptions.NoSuchElementException;
import com.healthchess.sigaapi.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

//Service tabela paciente
@Service
public class PacienteServiceImpl implements PacienteService {

    //Injeção de dependência
    @Autowired
    private PacienteRepository repository;

    //Método para buscar todos os pacientes
    public List<Paciente> bucarTodos(){
        return repository.findAll();
    }

    //Método para buscar paciente por id
    public Optional<Paciente> buscar(Integer id){
        Optional<Paciente> obj = repository.findById(id);
        return Optional.ofNullable(obj.orElseThrow(() -> new ObjectNotFoundException(
                "Paciente não encontrado! Id: " + id + " Tipo: " + Paciente.class.getName())));
    }

    //Método para excluir paciente por id
    public void excluir(Integer id){
        buscar(id);
        repository.deleteById(id);
    }

    //Método para criar um novo paciente.
    public Paciente salvar(@Valid Paciente paciente){
        if(buscarPorCPF(paciente) != null){
            throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
        }
        return repository.save(new Paciente(null, paciente.getNome(), paciente.getDataNascimento(),paciente.getCpf()));
    }

    //Método para atualizar dados do paciente
    public Paciente atualizar(Integer id, @Valid Paciente paciente){
        Optional<Paciente> pacienteAtualizar = repository.findById(id);
        if(buscarPorCPF(paciente) != null && buscarPorCPF(paciente).getId() != id){
            throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
        }
        if(!(buscar(id).isPresent())){
            throw new NoSuchElementException("Paciente não encontrado! Id: " + id +" Tipo: " + Paciente.class.getName());
        }

        pacienteAtualizar.get().setNome(paciente.getNome());
        pacienteAtualizar.get().setCpf(paciente.getCpf());
        pacienteAtualizar.get().setDataNascimento(paciente.getDataNascimento());

        return repository.save(pacienteAtualizar.get());
    }

    private Paciente buscarPorCPF(Paciente paciente){
        Paciente obj = repository.findByCPF(paciente.getCpf());
        if(obj != null){
            return obj;
        }
        return null;
    }

}
