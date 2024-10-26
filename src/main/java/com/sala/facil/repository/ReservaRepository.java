package com.sala.facil.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.sala.facil.entity.Usuario;
import com.sala.facil.entity.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findAllByUsuarioId(Long id);

    List<Reserva> findAllBySalaId(Long id);

    boolean existsByUsuarioAndStatus(Usuario usuario, int status);

}
