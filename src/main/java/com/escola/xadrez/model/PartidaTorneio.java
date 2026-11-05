package com.escola.xadrez.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "partida_torneio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartidaTorneio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mesa_numero", nullable = false)
    private Integer mesaNumero;

    @Column(name = "jogador_brancas", nullable = false, length = 100)
    private String jogadorBrancas;

    @Column(name = "jogador_negras", nullable = false, length = 100)
    private String jogadorNegras;

    @Column(name = "vencedor", nullable = false, length = 20)
    private String vencedor;

    @Column(name = "tempo_gasto_minutos")
    private Integer tempoGastoMinutos;

    @Column(name = "finalizada", nullable = false)
    private Boolean finalizada;
}