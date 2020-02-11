package com.merkmal.apigate;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthzController {

	private static final String template = "health, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/healthz")
	public Healthz healthz(@RequestParam(value = "name", defaultValue = "not really") String name) {
		return new Healthz(counter.incrementAndGet(), String.format(template, name));
	}
}
