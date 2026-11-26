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

   // 4 - Atualizar partida
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
    // 6 - Declarar resultado da partida
    @PatchMapping("/{id}/declarar-resultado")
    public ResponseEntity<?> declararResultado(@PathVariable Long id, @RequestBody PartidaTorneio dados) {

        Optional<PartidaTorneio> partidaExistente = repository.findById(id);

        if (partidaExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PartidaTorneio partida = partidaExistente.get();

        if (partida.getFinalizada()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Esta partida já foi concluída e não permite alterações.");
        }

        String resultado = dados.getVencedor();

        if (!resultado.equals("BRANCAS") && !resultado.equals("NEGRAS") && !resultado.equals("EMPATE")) {

            return ResponseEntity.badRequest()
                    .body("Opção de vencedor inválida! Use BRANCAS, NEGRAS ou EMPATE.");
        }

        if (dados.getTempoGastoMinutos() == null || dados.getTempoGastoMinutos() <= 0) {

            return ResponseEntity.badRequest()
                    .body("O tempo gasto deve ser maior que zero.");
        }

        partida.setVencedor(resultado);
        partida.setTempoGastoMinutos(dados.getTempoGastoMinutos());
        partida.setFinalizada(true);

        return ResponseEntity.ok(repository.save(partida));
    }
}