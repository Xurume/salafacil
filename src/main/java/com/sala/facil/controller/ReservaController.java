package com.sala.facil.controller;

import java.time.LocalDateTime;
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

import com.sala.facil.service.ReservaService;
import com.sala.facil.entity.Reserva;
import com.sala.facil.entity.Sala;
import com.sala.facil.entity.Usuario;
import com.sala.facil.repository.ReservaRepository;
import com.sala.facil.repository.SalaRepository;
import com.sala.facil.repository.UsuarioRepository;

import java.util.List;

@RestController
@RequestMapping(value = "reserva")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SalaRepository salaRepository;

    @GetMapping
    public List<Reserva> getReservas() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<?> criarReserva(@RequestBody Reserva reserva) {
        // data e hora atual
    LocalDateTime dataAtual = LocalDateTime.now();
    LocalDateTime dataReserva = reserva.getData_reserva();


    // Verificar se a data da reserva é anterior à data atual
    if (dataReserva.isBefore(dataAtual)) {
        return ResponseEntity.badRequest().body("Não é permitido reservar uma sala em uma data passada.");
    }

    // Verificar se a reserva é feita com mais de 30 dias de antecedência
    if (dataReserva.isAfter(dataAtual.plusDays(30))) {
        return ResponseEntity.badRequest().body("Não é permitido reservar uma sala com mais de 30 dias de antecedência.");
    }

    // Verificar se a sala já existe no banco de dados
    Optional<Sala> salaOptional = salaRepository.findById(reserva.getSala().getId_sala());
    if (!salaOptional.isPresent() || salaOptional.get().getStatus() != 1) { // Verifica se a sala está ativa
        return ResponseEntity.badRequest().body("A sala informada não existe ou não está ativa.");
    }

    // Verificar se o usuário já existe
    Optional<Usuario> usuarioOptional = usuarioRepository.findById(reserva.getUsuario().getId_usuario());
    if (!usuarioOptional.isPresent()) {
        return ResponseEntity.badRequest().body("O usuário informado não existe.");
    }

    // Atribuir a sala e o usuário existentes à reserva
    reserva.setSala(salaOptional.get());
    reserva.setUsuario(usuarioOptional.get());

        try {
            Reserva novaReserva = service.saveReserva(reserva);
            return new ResponseEntity<>(novaReserva, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // retorna erro se já existir uma reserva ativa
        }
    }

    // findByID
    @GetMapping("/{id}")
    public Reserva findReservaById(@PathVariable Long id) {
        Reserva reserva = service.findById(id);
        if (reserva == null) {
            throw new RuntimeException("Reserva não encontrada com ID: " + id);
        }
        return reserva;
    }

    // Delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    // Editar
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> atualizarReserva(@PathVariable Long id, @RequestBody Reserva reservaAtualizada) {
        Optional<Reserva> reservaOptional = repository.findById(id);

        if (!reservaOptional.isPresent()) {
            return ResponseEntity.notFound().build(); // Retorna 404 se a reserva não for encontrada
        }

        Reserva reservaExistente = reservaOptional.get();

        // Atualizar os campos da reserva
        reservaExistente.setData_reserva(reservaAtualizada.getData_reserva());
        reservaExistente.setData_pedido(reservaAtualizada.getData_pedido());
        reservaExistente.setStatus(reservaAtualizada.getStatus());

        // Atualizar o usuario
        if (reservaAtualizada.getUsuario() != null) {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(reservaAtualizada.getUsuario().getId_usuario());
            if (!usuarioOptional.isPresent()) {
                return ResponseEntity.notFound().build(); // Retorna 404 se o usuário não for encontrado
            }
            reservaExistente.setUsuario(usuarioOptional.get());
        }
    
        // Atualizar a sala
        if (reservaAtualizada.getSala() != null) {
            Optional<Sala> salaOptional = salaRepository.findById(reservaAtualizada.getSala().getId_sala());
            if (!salaOptional.isPresent()) {
                return ResponseEntity.notFound().build(); // Retorna 404 se a sala não for encontrada
            }
            reservaExistente.setSala(salaOptional.get());
        }

        // Salvar as mudanças no banco de dados
        Reserva reservaSalva = repository.save(reservaExistente);

        // Retornar a reserva atualizada
        return ResponseEntity.ok(reservaSalva);
    }

    // IMPLEMENTAR /RESERVA/{USERID}/USUARIO (GET) - TRAS AS RESERVAS QUE O USUARIO
    // FEZ
    @GetMapping("/{id}/usuario")
    public ResponseEntity<?> findReservasByUsuario(@PathVariable Long id) {
        List<Reserva> reservas = service.findByUsuarioId(id);
        if (reservas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nenhuma reserva encontrada para o usuario com ID: " + id);
        }
        return ResponseEntity.ok(reservas);
    }

    // IMPLEMENTAR /RESERVA/{SALAID}/SALA (GET) - TRAS AS RESERVAS QUE
    // DETERMINADA SALA POSSUI
    @GetMapping("/{id}/sala")
    public ResponseEntity<?> findReservasBySala(@PathVariable Long id) {
        List<Reserva> reservas = service.findBySalaId(id);
        if (reservas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nenhuma sala encontrada para a reserva com ID: " + id);
        }
        return ResponseEntity.ok(reservas);
    }

}
