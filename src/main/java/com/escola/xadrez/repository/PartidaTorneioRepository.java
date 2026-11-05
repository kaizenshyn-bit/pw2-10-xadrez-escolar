package com.escola.xadrez.repository;

import com.escola.xadrez.model.PartidaTorneio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaTorneioRepository extends JpaRepository<PartidaTorneio, Long> {
}