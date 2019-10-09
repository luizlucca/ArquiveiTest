package com.llucca.arquive.worker.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Padrao da mensagem postada na fila.
 * Note que deixei generico para poder ser de qualquer tipo.
 *
 * @param <T> Tipo a ser definido.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message<T> {
    T body;
    Long timestamp;
}
