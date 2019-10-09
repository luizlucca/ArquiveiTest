package com.llucca.arquivei.adapter;

import com.llucca.arquivei.model.client.NfeEncoded;
import com.llucca.arquivei.model.message.Message;
import com.llucca.arquivei.model.message.nfe.NfeMessage;

/**
 * Trasnforma uma nota fiscal em base64 para uma notal fiscal em xml
 */

public interface NfeAdapter {
    Message<NfeMessage> convertFromNfeEncodedToMessage(NfeEncoded nfeEncoded);
}
