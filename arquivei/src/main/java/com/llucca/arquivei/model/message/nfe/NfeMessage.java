package com.llucca.arquivei.model.message.nfe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Body da mensagem a ser enviada para a fila.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NfeMessage {
    private String accessKey;
    private String xml;
}
