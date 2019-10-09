package com.llucca.arquivei.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mensagem que sera postada na fila
 *
 * @param <T> Tipo da mensagem a ser postada na fila
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message<T> {
    T body;
    Long timestamp;
}
