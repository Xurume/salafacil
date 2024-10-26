package com.sala.facil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.sala.facil.repository.ReservaRepository;
import com.sala.facil.entity.Reserva;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    public List<Reserva> findAll() {
        return repository.findAll();
    }

    public Reserva saveReserva(Reserva reserva) {
        boolean reservaAtivaExiste = repository.existsByUsuarioAndStatus(reserva.getUsuario(), 1); // status 1 = ativa
        if (reservaAtivaExiste) {
            throw new RuntimeException("O usuário já possui uma reserva ativa. Não é possível criar uma nova reserva.");
        }
        return repository.save(reserva);
    }

    public Reserva findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Reserva> findByUsuarioId(Long id) {
        return repository.findAllByUsuarioId(id);
    }

    public List<Reserva> findBySalaId(Long id){
        return repository.findAllBySalaId(id);
    }

}
