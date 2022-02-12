package com.healthchess.sigaapi.controller;

import com.healthchess.sigaapi.model.Paciente;
import com.healthchess.sigaapi.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//Controller tabela paciente
@RestController
@RequestMapping("/paciente")
public class PacienteController {

    //Injeção de dependência
    @Autowired
    private PacienteService service;

    //Busca e retorna todos os pacientes.
    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos(){ return ResponseEntity.ok(service.bucarTodos());}

    //Busca o paciente por id
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Integer id){
        ResponseEntity<Paciente> response = null;
        if(service.buscar(id).isPresent()) {
            Paciente paciente = service.buscar(id).orElse(null);
            response = ResponseEntity.ok(paciente);
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
    public ResponseEntity<Paciente> registrar(@Valid @RequestBody Paciente paciente){
        return new ResponseEntity<Paciente>(service.salvar(paciente), HttpStatus.CREATED);
    }

    //Atualiza os dados de paciente
    @PutMapping
    public ResponseEntity<Paciente> atualizar(@Valid @RequestBody Paciente paciente) {
        ResponseEntity<Paciente> response = null;

        if (paciente.getId() != null && service.buscar(paciente.getId()).isPresent()) {
            response = new ResponseEntity<Paciente>(service.atualizar(paciente), HttpStatus.CREATED);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

}
