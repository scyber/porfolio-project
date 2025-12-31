package com.example.functions;

import java.util.Properties;

import org.apache.flink.api.common.functions.RichMapFunction;

import com.example.model.Enrichment;
import com.example.model.EnrichedEvent;
import com.example.model.RawUserEvent;

public class EnrichFunction extends RichMapFunction<RawUserEvent, EnrichedEvent> {

    private static final long serialVersionUID = 1L;


    @Override
    public EnrichedEvent map(RawUserEvent userEvent) {
        
        return enrich(userEvent);
    }

    private EnrichedEvent enrich(RawUserEvent userEvent) {
		
    	Enrichment enrichment = new Enrichment(userEvent.context().category(), userEvent.context().device(), userEvent.context().geo());
		
    	EnrichedEvent enrichedEvent = new EnrichedEvent(userEvent.eventId(), userEvent.userId(), userEvent.eventType(), 
    			userEvent.timestamp(),
    			userEvent.context(), enrichment
    			);
    	
    	
    	return enrichedEvent;
	}


}