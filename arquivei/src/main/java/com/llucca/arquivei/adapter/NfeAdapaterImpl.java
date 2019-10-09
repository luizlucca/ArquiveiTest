package com.llucca.arquivei.adapter;

import com.llucca.arquivei.model.client.NfeEncoded;
import com.llucca.arquivei.model.message.Message;
import com.llucca.arquivei.model.message.nfe.NfeMessage;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component("NfeAdapter")
public class NfeAdapaterImpl implements NfeAdapter {
    @Override
    public Message<NfeMessage> convertFromNfeEncodedToMessage(NfeEncoded nfeEncoded) {
        Message<NfeMessage> message = new Message<>();
        NfeMessage nfeMessage = new NfeMessage(nfeEncoded.getAccessKey(), new String(Base64.decodeBase64(nfeEncoded.getXml().getBytes())));

        message.setTimestamp(ZonedDateTime.now().toInstant().toEpochMilli());
        message.setBody(nfeMessage);
        return message;
    }
}
