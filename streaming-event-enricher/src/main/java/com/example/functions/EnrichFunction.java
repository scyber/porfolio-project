package com.example.functions;

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

		Enrichment enrichment = new Enrichment(userEvent.getContext().category(), userEvent.getContext().device(),
				userEvent.getContext().geo());

		EnrichedEvent enrichedEvent = new EnrichedEvent(userEvent.getEventId(), userEvent.getUserId(),
				userEvent.getEventType(), userEvent.getTimestamp(), userEvent.getContext(), enrichment);

		return enrichedEvent;
	}

}