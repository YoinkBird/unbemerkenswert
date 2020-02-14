package com.merkmal.apigate;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	private static final String template = "Hello, %s!";
	//private final AtomicLong counter = new AtomicLong();

	@GetMapping("/demo")
	public Demo demo(@RequestParam(value = "name", defaultValue = "World") String name) {
		//return new Demo(counter.incrementAndGet(), String.format(template, name));
		return new Demo(String.format(template, name));
	}
}
