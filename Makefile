# ============================
# Event-Driven Personalization Platform
# Makefile for local development
# ============================

# ---- Infra ----

infra-up:
	cd infra && docker-compose up -d

infra-down:
	cd infra && docker-compose down

infra-logs:
	docker-compose -f infra/docker-compose.yml logs -f

# ---- Build ----

build:
	mvn clean install -DskipTests

clean:
	mvn clean

# ---- Run Services ----

run-ingestion:
	cd ingestion-service && mvn spring-boot:run

run-api:
	cd personalization-api && mvn spring-boot:run

# ---- Run Flink Jobs ----

run-enricher:
	cd streaming-event-enricher && java -jar target/streaming-event-enricher-1.0.0-SNAPSHOT.jar

run-aggregator:
	cd streaming-profile-aggregator && java -jar target/streaming-profile-aggregator-1.0.0-SNAPSHOT.jar

run-writer:
	cd streaming-profile-writer && java -jar target/streaming-profile-writer-1.0.0-SNAPSHOT.jar

# ---- Logs ----

logs-kafka:
	docker logs kafka

logs-postgres:
	docker logs postgres

logs-redis:
	docker logs redis

# ---- Test Events ----

send-event:
	curl -X POST http://localhost:8081/events \
	  -H "Content-Type: application/json" \
	  -d '{"userId":"user123","eventType":"VIEW","timestamp":1714060800000,"context":{"page":"home","itemId":"item001","category":"tech","device":"mobile","geo":"de"}}'

# ---- End-to-End ----

e2e: infra-up build
	@echo ""
	@echo "Infrastructure is up and project is built."
	@echo "Start services in separate terminals:"
	@echo "  make run-ingestion"
	@echo "  make run-api"
	@echo "  make run-enricher"
	@echo "  make run-aggregator"
	@echo "  make run-writer"
	@echo ""
	@echo "Then send a test event:"
	@echo "  make send-event"
	@echo ""