package dev.sobue.rabbit.consumer;

import io.micrometer.tracing.brave.bridge.BraveTracer;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import static java.util.Objects.requireNonNull;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private static final String BAGGAGE_TAG_NAME = "sample-msg-process-time-millis";

	private final BraveTracer tracer;

	@Bean
	public Consumer<Message<String>> input() {
		return m -> {
			var baggage = tracer.getBaggage(BAGGAGE_TAG_NAME);
			var time = System.currentTimeMillis();
			var duration = time - Long.parseLong(requireNonNull(requireNonNull(baggage).get()));

			log.info("received. time:{} duration:{} payload:{}", time, duration, m.getPayload());
		};
	}
}
