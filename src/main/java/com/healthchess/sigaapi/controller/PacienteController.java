package com.healthchess.sigaapi.controller;

import com.healthchess.sigaapi.dtos.PacienteDTO;
import com.healthchess.sigaapi.model.Paciente;
import com.healthchess.sigaapi.service.impl.PacienteServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
        Paciente obj = service.buscar(id).get();
        return ResponseEntity.ok().body(paraPacienteDTO(obj));
    }

    //Deletar o paciente por id
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar o paciente por índice", description = "Esta operação deleta o paciente do referido índice do banco de dados.")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id){
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    //Criar um novo paciente
    @PostMapping
    @Operation(summary = "Criar um novo paciente", description = "Esta operação cria um novo paciente com os dados informados.")
    public ResponseEntity<PacienteDTO> registrar(@Valid @RequestBody PacienteDTO paciente){
        Paciente newObj = service.salvar(paraPaciente(paciente));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newObj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    //Atualizar os dados de paciente
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar os dados de paciente", description = "Esta operação atualiza o referido paciente com os dados informados.")
    public ResponseEntity<PacienteDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody PacienteDTO paciente) {
        PacienteDTO newObj = new PacienteDTO(service.atualizar(id, paraPaciente(paciente)));
        return ResponseEntity.ok().body(newObj);
    }

    private PacienteDTO paraPacienteDTO(Paciente paciente){
        return modelMapper.map(paciente, PacienteDTO.class);
    }

    private Paciente paraPaciente(PacienteDTO paciente){
        return modelMapper.map(paciente, Paciente.class);
    }


}
