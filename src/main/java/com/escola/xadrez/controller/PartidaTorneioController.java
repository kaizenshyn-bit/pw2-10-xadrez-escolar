package com.escola.xadrez.controller;

import com.escola.xadrez.model.PartidaTorneio;
import com.escola.xadrez.repository.PartidaTorneioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/partidas-xadrez")
public class PartidaTorneioController {

    @Autowired
    private PartidaTorneioRepository repository;

    // 1 - Listar todas as partidas
    @GetMapping
    public ResponseEntity<List<PartidaTorneio>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    // 2 - Buscar partida por ID
    @GetMapping("/{id}")
    public ResponseEntity<PartidaTorneio> buscarPorId(@PathVariable Long id) {

        Optional<PartidaTorneio> partida = repository.findById(id);

        return partida.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3 - Cadastrar nova partida
    @PostMapping
    public ResponseEntity<PartidaTorneio> cadastrar(
            @RequestBody PartidaTorneio partida) {

        partida.setVencedor("PENDENTE");
        partida.setFinalizada(false);

        PartidaTorneio novaPartida = repository.save(partida);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaPartida);
    }

   ///s 4 - Atualizar partida
    @PutMapping("/{id}")
    public ResponseEntity<PartidaTorneio> atualizar(
            @PathVariable Long id,
            @RequestBody PartidaTorneio dados) {

        Optional<PartidaTorneio> partidaExistente = repository.findById(id);

        return partidaExistente.map(partida -> {

            partida.setMesaNumero(dados.getMesaNumero());
            partida.setJogadorBrancas(dados.getJogadorBrancas());
            partida.setJogadorNegras(dados.getJogadorNegras());

            return ResponseEntity.ok(repository.save(partida));

        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 5 - Excluir partida
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        Optional<PartidaTorneio> partida = repository.findById(id);

        if (partida.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}