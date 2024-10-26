package com.sala.facil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sala.facil.repository.SalaRepository;
import com.sala.facil.entity.Sala;

import java.util.List;

@Service
public class SalaService {

    @Autowired
    private SalaRepository repository;

    public List<Sala> findAll() {
        return repository.findAll();
    }

    public Sala saveSala(Sala sala) {
        return repository.save(sala);
    }

    public Sala findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Sala> findByReservaId(Long id){
        return repository.findAllReservaById(id);
    }

    

}
