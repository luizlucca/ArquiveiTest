package com.llucca.arquive.worker.model.message.nfe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Essa eh a mensagem que deve estar presente na fila.
 * Ou seja o body T da classe message, deve conter um JSON que representa essa classe.
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NfeMessage {
    private String accessKey;
    private String xml;
}