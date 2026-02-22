package com.example.service;

import com.example.model.RawUserEvent;

public interface IngestionService {

    void sendEvent(RawUserEvent event);
}
