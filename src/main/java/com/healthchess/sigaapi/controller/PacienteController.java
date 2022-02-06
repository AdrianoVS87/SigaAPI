package com.healthchess.sigaapi.controller;

import com.healthchess.sigaapi.model.Paciente;
import com.healthchess.sigaapi.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Paciente> buscarTodos(){ return service.bucarTodos();}

    //Busca o paciente por id
    @GetMapping("/{id}")
    public Paciente buscarPorId(@PathVariable Integer id){
        return service.buscar(id);
    }

    //Deleta o paciente por id
    @DeleteMapping("/{id}")
    public String deletarPorId(@PathVariable Integer id){
        service.excluir(id);
        return "Paciente deletado com sucesso!";
    }

    //Cria um novo paciente
    @PostMapping
    public Paciente registrar(@Valid @RequestBody Paciente paciente){
        return service.salvar(paciente);
    }

    //Atualiza os dados de paciente
    @PutMapping
    public Paciente atualizar(@Valid @RequestBody Paciente paciente){
        return service.atualizar(paciente);
    }

}
