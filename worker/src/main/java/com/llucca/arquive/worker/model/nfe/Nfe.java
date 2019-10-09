package com.llucca.arquive.worker.model.nfe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entidade a ser persisitida no banco.
 * Temos apenas 2 campos que interessam.
 * Um deles eh a chave de accesso (chave da tabela) e o outro eh o valor da nota.
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
