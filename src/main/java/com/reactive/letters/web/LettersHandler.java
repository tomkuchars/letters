package com.reactive.letters.web;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reactive.letters.database.Letters;
import com.reactive.letters.database.LettersRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class LettersHandler {

	private final LettersRepository lettersRepository;

	public LettersHandler(LettersRepository lettersRepository) {
		this.lettersRepository = lettersRepository;
	}

	public Mono<ServerResponse> letters(ServerRequest request) {
		Flux<Long> interval = Flux.interval(Duration.ofMillis(1000));
		Flux<Letters> lettersEvents = lettersRepository.findAll();
		Flux<Letters> zipped = Flux.zip(lettersEvents, interval, (key, value) -> key);
		return ServerResponse
				.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(zipped, Letters.class);
	}

	public Mono<ServerResponse> last(ServerRequest request) {
		Mono<Letters> lastEvent = lettersRepository.findFirstByOrderByIdDesc();
		return ServerResponse
				.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(lastEvent, Letters.class);
	}

}
