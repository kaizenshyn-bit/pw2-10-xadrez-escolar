CREATE DATABASE EscolaDB;


USE EscolaDB;


CREATE TABLE partida_torneio (
     id BIGINT IDENTITY(1,1) PRIMARY KEY,
     mesa_numero INT NOT NULL,
     jogador_brancas VARCHAR(100) NOT NULL,
     jogador_negras VARCHAR(100) NOT NULL,
     vencedor VARCHAR(20) NOT NULL,
     tempo_gasto_minutos INT NULL,
     finalizada BIT NOT NULL
);
GO