package com.springboot.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(ReactorApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux nombres = Flux.just("Andres", "Pedro", "","Diego", "Juan")
				.doOnNext(e -> {
					if (e.isEmpty()){
						throw new RuntimeException("Los nombres no pueden estar vacios");
					}
					System.out.println(e);

				});

		nombres.subscribe(e -> log.info(e.toString()),
				error -> log.error(error.toString()));

	}
}
