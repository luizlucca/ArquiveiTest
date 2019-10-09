package com.llucca.arquivei.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entidade a ser consultada do banco de dados.
 * Veja que antes de bater no banco, tem um cache para melhorar a performance e resiliencia da aplicacao
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Nfe {

    @Id
    @Column(name = "access_key")
    private String accessKey;

    @Column(name = "total_value")
    private Double totalValue;
}
