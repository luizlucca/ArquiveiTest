package com.llucca.arquive.worker.consumers;

public interface Consumer {
    void receiveMessage(byte[] payload);
}
