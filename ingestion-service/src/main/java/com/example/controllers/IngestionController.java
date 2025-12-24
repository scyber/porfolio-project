package com.example.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.RawUserEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ApiResponses({ @ApiResponse(responseCode = "200", description = "Event recieved and Send"),
		@ApiResponse(responseCode = "400", description = "Incorrect Event Format") })

@RestController
@RequestMapping("/ingest")
public class IngestionController {

	private static final Logger logger = LoggerFactory.getLogger(IngestionController.class);

	@Operation(summary = "Receive user events ", description = "Save and send Raw user Events to streaming platform")
	@PostMapping("/event")
	public ResponseEntity<String> postUserEventsContex(@RequestBody RawUserEvent userEvent) {
		logger.info("Recieve event {}", userEvent);

		// ToDo add kafka send user Event with success/fail result response

		return ResponseEntity.ok("User Event Recieved");
	}

}
