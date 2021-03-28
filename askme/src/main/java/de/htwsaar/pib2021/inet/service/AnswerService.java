package de.htwsaar.pib2021.inet.service;

import java.util.Optional;

import de.htwsaar.pib2021.inet.model.Answer;

public interface AnswerService {
	Answer save(Answer answer);

	Answer saveAndFlush(Answer answer);

	Optional<Answer> findById(long answerId);

	void deleteById(Long answerId);
}
