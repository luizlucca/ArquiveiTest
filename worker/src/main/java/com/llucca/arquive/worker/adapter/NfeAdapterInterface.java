package com.llucca.arquive.worker.adapter;

import com.llucca.arquive.worker.model.message.nfe.NfeMessage;
import com.llucca.arquive.worker.model.nfe.Nfe;

public interface NfeAdapterInterface {

    public Nfe convertFromNfeMessage(NfeMessage nfeMessage);
}
