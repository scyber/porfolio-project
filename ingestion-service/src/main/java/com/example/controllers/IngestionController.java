package com.example.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.api.dto.Resp;
import com.example.model.RawUserEvent;
import com.example.services.KafkaProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;



@ApiResponses({ @ApiResponse(responseCode = "200", description = "Event recieved and Send"),
		@ApiResponse(responseCode = "400", description = "Incorrect Event Format or Faile send Event due to Kafka not availability") })

@RestController
@RequestMapping("/ingest")
public class IngestionController {

	@Autowired
	private KafkaProducerService kafkaProducerService;

	private static final Logger logger = LoggerFactory.getLogger(IngestionController.class);

	@Operation(summary = "Receive user events ", description = "Save and send Raw user Events to streaming platform")
	@PostMapping("/event")
	public ResponseEntity<Resp<RawUserEvent>> postUserEventsContex(@Valid @RequestBody RawUserEvent userEvent) {

		logger.info("Received event: {}", userEvent);

		kafkaProducerService.sendEvent(userEvent);

		return ResponseEntity.ok(Resp.ok("User Event Successfully sent", userEvent));
	}

}
