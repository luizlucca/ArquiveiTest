package com.llucca.arquivei.repository;

import com.llucca.arquivei.model.Nfe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio que controla o acesso ao banco
 */

@Repository
public interface NfeRepository extends JpaRepository<Nfe, String> {
    Optional<Nfe> findByAccessKey(String accessKey);
}
