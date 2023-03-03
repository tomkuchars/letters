package com.reactive.letters.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.reactive.letters.database.Letters;
import com.reactive.letters.database.LettersRepository;

@Service
public class LettersService {
	private final LettersRepository lettersRepository;

	public LettersService(LettersRepository lettersRepository) {
		this.lettersRepository = lettersRepository;
	}

	@Scheduled(fixedRate = 5000) // 5 seconds
	public void create() {
		Letters letters = new Letters();
		letters.setCreated(System.currentTimeMillis());
		letters.setLetter(randomLetter());
		lettersRepository.save(letters)
				.log()
				.then()
				.subscribe();
	}

	private static String randomLetter() {
		int lower = 'A';
		int upper = 'Z';
		int range = upper - lower;
		char charIdx = ((char) (lower + (range * Math.random())));
		return String.valueOf(charIdx);
	}
}
