package com.llucca.arquive.worker.adapter;

import com.llucca.arquive.worker.exception.ConverterException;
import com.llucca.arquive.worker.model.message.nfe.NfeMessage;
import com.llucca.arquive.worker.model.nfe.Nfe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Trasnforma uma mensagem recebida em uma nota fiscal
 */

@Slf4j
@Component("NfeAdpater")
public class NfeAdapter implements NfeAdapterInterface {
    @Override
    public Nfe convertFromNfeMessage(NfeMessage nfeMessage) {
        try {
            String xml = nfeMessage.getXml();
            Double valNf = Double.parseDouble(xml.substring(xml.indexOf("<vNF>") + 5, xml.indexOf("</vNF>")));

            return new Nfe(nfeMessage.getAccessKey(), valNf);
        } catch (Exception e) {
            log.error("Error convertinga data from NfeMessage to Nfe", e);
            throw new ConverterException("Error convertinga data from NfeMessage to Nfe");
        }
    }
}
