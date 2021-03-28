package de.htwsaar.pib2021.inet.service;

import java.util.List;
import java.util.Optional;

import de.htwsaar.pib2021.inet.model.Question;

public interface QuestionService {

	Optional<Question> findById(Long questionId);
	
	List<Question> findByTestId(Long testId);

	Question save(Question question);
	
	void deleteById(Long quesstionId);
}
