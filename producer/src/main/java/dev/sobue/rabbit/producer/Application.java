package dev.sobue.rabbit.producer;

import io.micrometer.tracing.brave.bridge.BraveTracer;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

@RestController
@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private static final String BAGGAGE_TAG_NAME = "sample-msg-process-time-millis";

	private final StreamBridge streamBridge;

	private final BraveTracer tracer;

	@PostMapping("/post-message")
	public ResponseEntity<Void> postMessage(@RequestBody String body) {
		var baggage = tracer.getBaggage(BAGGAGE_TAG_NAME);
		var time = System.currentTimeMillis();
		requireNonNull(baggage).set(String.valueOf(time));

		log.info("send. time:{} payload:{}", time, body);

		streamBridge.send(
				"output-out-0",
				MessageBuilder.withPayload(body).build());

		return ResponseEntity.accepted().build();
	}

	//@Bean
	//public Supplier<Message<String>> output() {
	//	return () -> {
	//		var baggage = tracer.getBaggage(BAGGAGE_TAG_NAME);
	//		var time = System.currentTimeMillis();
	//		requireNonNull(baggage).set(String.valueOf(time));

	//		var body = UUID.randomUUID().toString();
	//		log.info("send. time:{} payload:{}", time, body);
	//		return MessageBuilder.withPayload(body).build();
	//	};
	//}
}
