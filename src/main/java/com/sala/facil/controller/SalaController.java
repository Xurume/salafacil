package com.sala.facil.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sala.facil.service.SalaService;
import com.sala.facil.entity.Sala;
import com.sala.facil.repository.SalaRepository;

import java.util.List;

@RestController
@RequestMapping(value = "sala")
public class SalaController {

    @Autowired
    private SalaService service;

    @Autowired
    private SalaRepository repository;

    @GetMapping
    public List<Sala> getSalas() {
        return service.findAll();
    }

    @PostMapping
    public Sala saveSala(@RequestBody Sala sala) {
        Sala salaSalva = service.saveSala(sala);
        return salaSalva;
    }

    // findByID
    @GetMapping("/{id}")
    public Sala findSalaById(@PathVariable Long id) {
        Sala sala = service.findById(id);
        if (sala == null) {
            throw new RuntimeException("Sala não encontrada com ID: " + id);
        }
        return sala;
    }

    // Delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    //Editar
    @PutMapping("/{id}")
    public ResponseEntity<Sala> atualizarSala(@PathVariable Long id, @RequestBody Sala salaAtualizada) {
        Optional<Sala> salaOptional = repository.findById(id);

        if (!salaOptional.isPresent()) {//Sala não presente
            return ResponseEntity.notFound().build(); // Retorna 404 se a sala não for encontrada
        }

        Sala salaExistente = salaOptional.get();
        
        // Atualizar os campos da sala
        salaExistente.setNome(salaAtualizada.getNome());
        salaExistente.setDepartamento(salaAtualizada.getDepartamento());
        salaExistente.setDescricao(salaAtualizada.getDescricao());
        salaExistente.setStatus(salaAtualizada.getStatus());

        // Salvar as mudanças no banco de dados
        Sala salaSalva = repository.save(salaExistente);
        
        // Retornar a sala atualizada
        return ResponseEntity.ok(salaSalva);
    }

}
