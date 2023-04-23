package dev.sobue.rabbit.consumer;

import io.micrometer.tracing.brave.bridge.BraveTracer;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private final BraveTracer tracer;

	@Bean
	public Consumer<Message<String>> input() {
		return m -> {
			var baggage = tracer.getBaggage("sample-msg-process-time-millis");
			var t = System.currentTimeMillis() - Long.parseLong(baggage.get());

			log.info("d:{} i:{}", t, m);
		};
	}
}
