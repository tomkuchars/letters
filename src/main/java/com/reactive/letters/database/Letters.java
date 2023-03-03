package com.reactive.letters.database;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Letters {

	@Id
	private Long id;
	private Long created;
	private String letter;

}
