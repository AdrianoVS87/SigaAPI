package com.healthchess.sigaapi.controller;

import com.healthchess.sigaapi.model.Paciente;
import com.healthchess.sigaapi.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @GetMapping
    public List<Paciente> buscarTodos(){ return service.bucarTodos();}

    @GetMapping("/{id}")
    public Paciente buscarPorId(@PathVariable Integer id){
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    public String deletarPorId(@PathVariable Integer id){
        service.excluir(id);
        return "Paciente deletado com sucesso!";
    }

    @PostMapping
    public Paciente registrar(@Valid @RequestBody Paciente paciente){
        return service.salvar(paciente);
    }

    @PutMapping
    public Paciente atualizar(@Valid @RequestBody Paciente paciente){
        return service.atualizar(paciente);
    }

}
