package de.htwsaar.pib2021.inet.service.serviceImpl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import de.htwsaar.pib2021.inet.model.Answer;
import de.htwsaar.pib2021.inet.repository.AnswerRepository;
import de.htwsaar.pib2021.inet.service.AnswerService;

@Service
@Qualifier("answerServiceImpl")
@Transactional
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private AnswerRepository answerRepository;

	@Override
	public Answer save(Answer answer) {
		return answerRepository.save(answer);
	}

	public Answer saveAndFlush(Answer answer) {
		return answerRepository.saveAndFlush(answer);
	}

	@Override
	public Optional<Answer> findById(long answerId) {
		return answerRepository.findById(answerId);
	}

	@Override
	public void deleteById(Long answerId) {
		answerRepository.deleteById(answerId);
	}

}
