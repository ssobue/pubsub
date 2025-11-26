package dev.sobue.rabbit.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.micrometer.metrics.test.autoconfigure.AutoConfigureMetrics;
import org.springframework.boot.micrometer.tracing.test.autoconfigure.AutoConfigureTracing;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMetrics
@AutoConfigureTracing
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
