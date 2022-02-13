package com.healthchess.sigaapi.controller;

import com.healthchess.sigaapi.dtos.PacienteDTO;
import com.healthchess.sigaapi.model.Paciente;
import com.healthchess.sigaapi.service.impl.PacienteServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

//Controller tabela paciente
@RestController
@RequestMapping("/paciente")
public class PacienteController {

    //Injeção de dependências
    @Autowired
    private PacienteServiceImpl service;

    @Autowired
    private ModelMapper modelMapper;

    //Busca e retorna todos os pacientes.
    @GetMapping
    public ResponseEntity<List<PacienteDTO>> buscarTodos(){
        return ResponseEntity.ok(service.bucarTodos()
            .stream().map(this::paraPacienteDTO)
            .collect(Collectors.toList()));
    }

    //Busca o paciente por id
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPorId(@PathVariable Integer id){
        ResponseEntity<PacienteDTO> response = null;
        if(service.buscar(id).isPresent()) {
            Paciente paciente = service.buscar(id).orElse(null);
            response = ResponseEntity.ok(paraPacienteDTO(paciente));
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    //Deleta o paciente por id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarPorId(@PathVariable Integer id){
        ResponseEntity<String> response = null;

        if(service.buscar(id).isPresent()){
            service.excluir(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Paciente deletado");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    //Cria um novo paciente
    @PostMapping
    public ResponseEntity<PacienteDTO> registrar(@Valid @RequestBody PacienteDTO paciente){
        service.salvar(paraPaciente(paciente));
        return new ResponseEntity<PacienteDTO>(paciente, HttpStatus.CREATED);
    }

    //Atualiza os dados de paciente
    @PutMapping
    public ResponseEntity<PacienteDTO> atualizar(@Valid @RequestBody PacienteDTO paciente) {
        ResponseEntity<PacienteDTO> response = null;

        if (paciente.getId() != null && service.buscar(paciente.getId()).isPresent()) {
            service.atualizar(paraPaciente(paciente));
            response = new ResponseEntity<PacienteDTO>(paciente, HttpStatus.CREATED);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    private PacienteDTO paraPacienteDTO(Paciente paciente){
        return modelMapper.map(paciente, PacienteDTO.class);
    }

    private Paciente paraPaciente(PacienteDTO paciente){
        return modelMapper.map(paciente, Paciente.class);
    }


}
