package com.sala.facil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.util.List;

import com.sala.facil.entity.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long>{

    List<Sala> findAllReservaById(Long id);

    //@Query("SELECT r FROM Reserva r WHERE r.id = :id")
   // List<Reserva> findReservasById(@Param("id") Long id);
}
