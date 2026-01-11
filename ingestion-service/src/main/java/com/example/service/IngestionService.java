package com.example.services;

import com.example.model.RawUserEvent;

public interface IngestionService {
    
    void sendEvent(RawUserEvent event);
}
