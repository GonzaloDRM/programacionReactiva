package com.springboot.reactor;

import com.springboot.reactor.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ReactorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(ReactorApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<String> usuariosList = new ArrayList<>();
		usuariosList.add("Andres Guzman");
		usuariosList.add("Pedro Fulano");
		usuariosList.add("Mania Fulana");
		usuariosList.add("Diego Sultano");
		usuariosList.add("Juan Mengano");
		usuariosList.add("Bruce Lee");
		usuariosList.add("Bruce Willis");

		Flux<String> nombres = Flux.fromIterable(usuariosList); //Flux.just("Andres Guzman", "Pedro Fulano", "Mania Fulana","Diego Sultano", "Juan Mengano", "Bruce Lee", "Bruce Willis");

		Flux<Usuario> usuarios = nombres.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
				.filter(usuario -> {return usuario.getNombre().toLowerCase().equals("bruce");})
				.doOnNext(usuario -> {
					if (usuario == null){
						throw new RuntimeException("Los nombres no pueden estar vacios");
					}
					System.out.println(usuario.getNombre().concat(" ").concat(usuario.getAopellido()));

				})
				.map(usuario -> {
					String nombre = usuario.getNombre().toLowerCase();
					return usuario;
				});

		usuarios.subscribe(e -> log.info(e.getNombre()),
				error -> log.error(error.getMessage()),
				new Runnable() {
					@Override
					public void run() {
						log.info("Finalizo la ejecucion con exito");
					}
				});

	}
}
