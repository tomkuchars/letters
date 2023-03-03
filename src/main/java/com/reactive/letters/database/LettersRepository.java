package com.reactive.letters.database;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Mono;

public interface LettersRepository extends R2dbcRepository<Letters, Long> {

	Mono<Letters> findFirstByOrderByIdDesc();

}
