package com.sala.facil.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sala.facil.service.UsuarioService;
import com.sala.facil.entity.Usuario;
import com.sala.facil.repository.UsuarioRepository;

import java.util.List;

@RestController
@RequestMapping(value = "usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioRepository repository;
    
    @GetMapping
    public List<Usuario> getUsuarios(){
        return service.findAll();
    }

    //Salvar novo usuario
    @PostMapping
    public Usuario saveUsuario(@RequestBody Usuario usuario){
        Usuario usuarioSalvo = service.saveUsuario(usuario);
        return usuarioSalvo;
    }

    //findByID
    @GetMapping("/{id}")
    public ResponseEntity<?> findUsuarioById(@PathVariable Long id){
        Usuario usuario = service.findById(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado com ID: " + id);
        }
        return ResponseEntity.ok(usuario);
    }

    //Delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        service.deleteById(id);
    }

    //Editar
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado){

        Optional<Usuario> usuarioOptional = repository.findById(id);

        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o usuário não for encontrado
        }

        Usuario usuarioExistente = usuarioOptional.get();
        
        // Atualizar os campos do usuário
        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setCpf(usuarioAtualizado.getCpf());
        usuarioExistente.setPhone(usuarioAtualizado.getPhone());
        
        // Salvar as mudanças no banco de dados
        Usuario usuarioSalvo = repository.save(usuarioExistente);
        
        // Retornar o usuário atualizado
        return ResponseEntity.ok(usuarioSalvo);
    }

}
