package com.sala.facil.service;

//import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sala.facil.repository.UsuarioRepository;
import com.sala.facil.entity.Usuario;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> findAll(){
        return repository.findAll();
    }
    
    public Usuario saveUsuario(Usuario usuario){
        return repository.save(usuario);
    }

    public Usuario findById(Long id_usuario){
        return repository.findById(id_usuario).orElse(null);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
