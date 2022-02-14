package com.healthchess.sigaapi.service.impl;

import com.healthchess.sigaapi.dtos.PacienteDTO;
import com.healthchess.sigaapi.model.Paciente;
import com.healthchess.sigaapi.repository.PacienteRepository;
import com.healthchess.sigaapi.service.PacienteService;
import com.healthchess.sigaapi.service.exceptions.DataIntegrityViolationException;


import com.healthchess.sigaapi.service.exceptions.NoSuchElementException;
import com.healthchess.sigaapi.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Service tabela paciente
@Service
public class PacienteServiceImpl implements PacienteService {

    //Injeção de dependência
    @Autowired
    private PacienteRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    //Método para buscar todos os pacientes
    public List<PacienteDTO> bucarTodos(){
        List<Paciente> lista = repository.findAll();
        List<PacienteDTO> listaDTO = lista.stream().map(this::paraPacienteDTO)
                .collect(Collectors.toList());
        return listaDTO;
    }

    //Método para buscar paciente por id
    public PacienteDTO buscar(Integer id){
        Optional<Paciente> obj = repository.findById(id);
        Optional<PacienteDTO> objDTO = obj.map(this::paraPacienteDTO);
        return objDTO.orElseThrow(() -> new ObjectNotFoundException(
                "Paciente não encontrado! Id: " + id + " Tipo: " + Paciente.class.getName()));
    }

    //Método para excluir paciente por id
    public void excluir(Integer id){
        buscar(id);
        repository.deleteById(id);
    }

    //Método para criar um novo paciente.
    public PacienteDTO salvar(@Valid PacienteDTO paciente){
        if(buscarPorCPF(paciente) != null){
            throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
        }
        repository.save(paraPaciente(paciente));
        return paciente;
    }

    //Método para atualizar dados do paciente
    public PacienteDTO atualizar(Integer id, @Valid PacienteDTO paciente){
        if(buscarPorCPF(paciente) != null && buscarPorCPF(paciente).getId() != id){
            throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
        }
        if(!repository.findById(id).isPresent()){
            throw new NoSuchElementException("Paciente não encontrado! Id: " + id +" Tipo: " + Paciente.class.getName());
        }
        Paciente pacienteAtualizar = repository.findById(id).get();
        pacienteAtualizar.setNome(paciente.getNome());
        pacienteAtualizar.setCpf(paciente.getCpf());
        pacienteAtualizar.setDataNascimento(paciente.getDataNascimento());
        repository.save(pacienteAtualizar);
        return paraPacienteDTO(pacienteAtualizar);
    }

    private PacienteDTO buscarPorCPF(PacienteDTO paciente){
        Paciente obj = repository.findByCPF(paciente.getCpf());

        if(obj != null){
            return paraPacienteDTO(obj);
        }
        return null;
    }

    private PacienteDTO paraPacienteDTO(Paciente paciente){
        return modelMapper.map(paciente, PacienteDTO.class);
    }

    private Paciente paraPaciente(PacienteDTO paciente){
        return modelMapper.map(paciente, Paciente.class);
    }

}
