package de.htwsaar.pib2021.inet.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import de.htwsaar.pib2021.inet.model.Result;
import de.htwsaar.pib2021.inet.repository.ResultRepository;
import de.htwsaar.pib2021.inet.service.ResultService;

@Service
@Qualifier("resultServiceImpl")
@Transactional
public class ResultServiceImpl implements ResultService {

	@Autowired
	private ResultRepository resultRepository;

	@Override
	public Optional<Result> findById(Long id) {
		return resultRepository.findById(id);
	}

	@Override
	public Result save(Result result) {
		return resultRepository.save(result);
	}

	@Override
	public Result findByTestId(Long testId) {
		return resultRepository.findByTestId(testId);
	}

	@Override
	public List<Result> findByExamineeId(Long examineeId) {
		return resultRepository.findByExamineeId(examineeId);
	}

	@Override
	public List<Object[]> findAllByAdminId(Long admiId) {
		return resultRepository.findAllByAdminId(admiId);
	}

	@Override
	public void deleteByExamineeId(Long examineeId) {
		resultRepository.deleteByExamineeId(examineeId);
	}

}
