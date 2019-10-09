package com.llucca.arquive.worker.repository;

import com.llucca.arquive.worker.model.nfe.Nfe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Acesso ao banco
 * Busca por entidades do tipo NFE
 */

@Repository
public interface NfeRepository extends JpaRepository<Nfe, String> {
}
