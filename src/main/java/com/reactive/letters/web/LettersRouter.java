package com.reactive.letters.web;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Configuration(proxyBeanMethods = false)
public class LettersRouter {

	@Value("classpath:pages/letters.html")
	private Resource lettersHtml;

	@Bean
	public RouterFunction<ServerResponse> route(LettersHandler lettersHandler) {

		return RouterFunctions
				.route(GET("/"), request -> pageResponse(lettersHtml))
				.andRoute(GET("/letters"), lettersHandler::letters)
				.andRoute(GET("/last"), lettersHandler::last);
	}

	private static Mono<ServerResponse> pageResponse(Resource page) {
		return ServerResponse
				.ok()
				.contentType(MediaType.TEXT_HTML)
				.body(DataBufferUtils.read(page, new DefaultDataBufferFactory(), 4096), DataBuffer.class);
	}
}
