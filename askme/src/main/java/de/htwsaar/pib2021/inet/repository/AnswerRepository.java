package de.htwsaar.pib2021.inet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.htwsaar.pib2021.inet.model.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
