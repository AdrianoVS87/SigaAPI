package com.healthchess.sigaapi.controller;

import com.healthchess.sigaapi.dtos.PacienteDTO;
import com.healthchess.sigaapi.model.Paciente;
import com.healthchess.sigaapi.service.impl.PacienteServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
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

    //Buscar todos os pacientes.
    @GetMapping
    @Operation(summary = "Buscar todos os pacientes", description = "Esta operação retorna todos os pacientes do banco de dados.")
    public ResponseEntity<List<PacienteDTO>> buscarTodos(){
        return ResponseEntity.ok(service.bucarTodos()
            .stream().map(this::paraPacienteDTO)
            .collect(Collectors.toList()));
    }

    //Buscar o paciente por id
    @GetMapping("/{id}")
    @Operation(summary = "Buscar o paciente por índice", description = "Esta operação retorna o paciente do referido índice do banco de dados.")
    public ResponseEntity<PacienteDTO> buscarPorId(@PathVariable Integer id){
        ResponseEntity<PacienteDTO> response;
        if(service.buscar(id).isPresent()) {
            Paciente paciente = service.buscar(id).get();
            response = ResponseEntity.status(HttpStatus.OK).body(paraPacienteDTO(paciente));
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    //Deletar o paciente por id
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar o paciente por índice", description = "Esta operação deleta o paciente do referido índice do banco de dados.")
    public ResponseEntity<String> deletarPorId(@PathVariable Integer id){
        ResponseEntity<String> response;
        if(service.buscar(id).isPresent()){
            service.excluir(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Paciente deletado");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    //Criar um novo paciente
    @PostMapping
    @Operation(summary = "Criar um novo paciente", description = "Esta operação cria um novo paciente com os dados informados.")
    public ResponseEntity<PacienteDTO> registrar(@Valid @RequestBody PacienteDTO paciente){
        service.salvar(paraPaciente(paciente));
        return new ResponseEntity<>(paciente, HttpStatus.CREATED);
    }

    //Atualizar os dados de paciente
    @PutMapping
    @Operation(summary = "Atualizar os dados de paciente", description = "Esta operação atualiza o referido paciente com os dados informados.")
    public ResponseEntity<PacienteDTO> atualizar(@Valid @RequestBody PacienteDTO paciente) {
        ResponseEntity<PacienteDTO> response;
        if (paciente.getId() != null && service.buscar(paciente.getId()).isPresent()) {
            service.atualizar(paraPaciente(paciente));
            response = ResponseEntity.status(HttpStatus.OK).body(paciente);
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
