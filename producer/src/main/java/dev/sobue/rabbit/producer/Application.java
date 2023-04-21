package dev.sobue.rabbit.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.StringUtils.hasLength;

@RestController
@RequiredArgsConstructor
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private final StreamBridge streamBridge;

	@PostMapping("/post-message")
	public ResponseEntity<Void> postMessage(@RequestBody(required = false) String body) {
		streamBridge.send(
				"output-out-0",
				MessageBuilder.withPayload(hasLength(body) ? body : "a").build());

		return ResponseEntity.accepted().build();
	}

	//@Bean
	//public Supplier<Message<String>> output() {
  //		return () -> MessageBuilder.withPayload("a").build();
	//}
}
