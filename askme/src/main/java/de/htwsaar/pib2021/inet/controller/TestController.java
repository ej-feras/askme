package de.htwsaar.pib2021.inet.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.htwsaar.pib2021.inet.model.Answer;
import de.htwsaar.pib2021.inet.model.FormView;
import de.htwsaar.pib2021.inet.model.Question;
import de.htwsaar.pib2021.inet.model.QuestionWithSelectionListWrapper;
import de.htwsaar.pib2021.inet.model.Result;
import de.htwsaar.pib2021.inet.model.Test;
import de.htwsaar.pib2021.inet.model.User;
import de.htwsaar.pib2021.inet.security.UserDetailsImpl;
import de.htwsaar.pib2021.inet.service.QuestionService;
import de.htwsaar.pib2021.inet.service.ResultService;
import de.htwsaar.pib2021.inet.service.TestService;
import de.htwsaar.pib2021.inet.service.UserService;

@Controller
public class TestController {

	@Autowired
	@Qualifier("testServiceImpl")
	private TestService testService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	@Qualifier("questionServiceImpl")
	private QuestionService questionService;

	@Autowired
	@Qualifier("resultServiceImpl")
	private ResultService resultService;

	/**
	 *
	 *
	 * @param userDetails
	 * @param model
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	@RequestMapping(value = "/tests", method = RequestMethod.GET)
	public String viewTestsPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		Long id = userDetails.getId();
		List<Test> tests = testService.findAllByCreaterId(id);
		if (userDetails.getUser().getRole().equals("ROLE_USER"))
			tests = findAvailableUserTests(userDetails, id);

		FormView formView = new FormView();

		for (Test test : tests) {
			test.setUsersTobeTested(userService.findAllCandidatesForATestByAdminId(test.getId(), userDetails.getId()));
			formView.getTestsView().add(test);
		}

		Test test = new Test();
		model.addAttribute("test", test);
		model.addAttribute("tests", tests);
		model.addAttribute("formView", formView);
		model.addAttribute("user", userDetails.getUser());
		

		return "tests";
	}
	
	@RequestMapping(value = "/tests-list", method = RequestMethod.GET)
	public  @ResponseBody List<Test> getTests(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		Long id = userDetails.getId();
		List<Test> tests = testService.findAllByCreaterId(id);
		if (userDetails.getUser().getRole().equals("ROLE_USER"))
			tests = findAvailableUserTests(userDetails, id);

		FormView formView = new FormView();

		for (Test test : tests) {
			test.setUsersTobeTested(userService.findAllCandidatesForATestByAdminId(test.getId(), userDetails.getId()));
			formView.getTestsView().add(test);
		}

		Test test = new Test();
		model.addAttribute("test", test);
		model.addAttribute("tests", tests);
		model.addAttribute("formView", formView);

		return tests;
	}


	/**
	 *
	 *
	 * @param userDetails
	 * @param id
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	private List<Test> findAvailableUserTests(UserDetailsImpl userDetails, Long id) {
		List<Test> tests;
		id = userService.findAdminOf(userDetails.getId());
		tests = testService.findAllByCreaterId(id);
		List<Test> testToRemove = new ArrayList<Test>();
		for (Test test : tests) {
			boolean testAccess = testService.isAccessableByUser(test.getId(), userDetails.getId());
			if (!testAccess)
				testToRemove.add(test);
		}
		tests.removeAll(testToRemove);
		return tests;
	}

	/**
	 *
	 *
	 * @param userDetails
	 * @param test
	 * @param redirectAttributes
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	@RequestMapping(value = "/add-test", method = RequestMethod.POST)
	public String saveExam(@AuthenticationPrincipal UserDetailsImpl userDetails, @ModelAttribute("test") Test test,
			RedirectAttributes redirectAttributes) {
		Long id = userDetails.getId();
		Optional<User> user = userService.findById(id);
		if (user.isPresent()) {
			test.setTestCreator(user.get());
			testService.addTest(test);
		}

		redirectAttributes.addFlashAttribute("notification", "New Test successfully saved");
		redirectAttributes.addFlashAttribute("action", "saveTest");

		return "redirect:/tests";
	}

	@RequestMapping(value = "/edit-test", method = RequestMethod.POST)
	public String editExam(@ModelAttribute("test") Test test, RedirectAttributes redirectAttributes) {
		Optional<Test> foundTest = testService.findById(test.getId());
		if (foundTest.isPresent()) {
			foundTest.get().setName(test.getName());
			foundTest.get().setTime(test.getTime());
			foundTest.get().setModifyDate(LocalDateTime.now());
			testService.addTest(foundTest.get());
		}

		redirectAttributes.addFlashAttribute("notification", "Test was successfully updated");
		redirectAttributes.addFlashAttribute("action", "updateTest");
		return "redirect:/tests";
	}

	/**
	 *
	 *
	 * @param test
	 * @param redirectAttributes
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	@RequestMapping(value = "/delete-test")
	public String deleteTest(@ModelAttribute("test") Test test, RedirectAttributes redirectAttributes) {
		testService.deleteById(test.getId());
		
		redirectAttributes.addFlashAttribute("notification", "Test was successfully deleted");
		redirectAttributes.addFlashAttribute("action", "deleteTest");
		return "redirect:/tests";
	}

	/**
	 *
	 *
	 * @param userDetails
	 * @param model
	 * @param testId
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String startTest(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model,
			@RequestParam(value = "testId") Long testId) {
		Optional<Test> test = testService.findById(testId);

		if (test.isPresent()) {
			test.get().getUsersTobeTested().add(userDetails.getUser());
			testService.save(test.get());
			ArrayList<Question> questions = (ArrayList<Question>) questionService.findByTestId(testId);
			QuestionWithSelectionListWrapper wrapper = new QuestionWithSelectionListWrapper();
			wrapper.setQuestionsList(questions);
			model.addAttribute("wrapper", wrapper);
			model.addAttribute("testId", testId);
			model.addAttribute("test", test.get());
		}
		Long examineeId = userDetails.getId();
		testService.setTestAccess(testId, examineeId, 0);
		return "test";
	}

	/**
	 *
	 *
	 * @param userDetails
	 * @param wrapper
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	@RequestMapping(value = "/finish-test", method = RequestMethod.POST)
	public String finishTest(@AuthenticationPrincipal UserDetailsImpl userDetails,
			@ModelAttribute("wrapper") QuestionWithSelectionListWrapper wrapper) {
		List<Question> questionsWithSelectedAnswers = wrapper.getQuestionsList();
		Long examineeId = userDetails.getId();
		Test takenTest = questionsWithSelectedAnswers.get(0).getTest();
		Result result = createResult(takenTest, questionsWithSelectedAnswers, examineeId);
		resultService.save(result);
		testService.setTestAccess(takenTest.getId(), examineeId, 0);

		return "redirect:/tests";
	}

	/**
	 *
	 *
	 * @param takenTest
	 * @param questionsWithSelectedAnswers
	 * @param examineeId
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	private Result createResult(Test takenTest, List<Question> questionsWithSelectedAnswers, Long examineeId) {
		double fullPoints = 0.0;
		double grade = 0.0;
		for (Question question : questionsWithSelectedAnswers) {
			List<Answer> answers = question.getAnswers();
			boolean containsWrongAnswers = answers.stream().anyMatch(
					answer -> answer.isSelected() && !answer.isCorrect() || answer.isCorrect() && !answer.isSelected());
			if (!containsWrongAnswers)
				grade += question.getPoints();

			fullPoints += question.getPoints();
		}

		Long testId = takenTest.getId();
		String testName = takenTest.getName();
		double totalMark = takenTest.getTotalMark();
		boolean passed = grade >= (fullPoints * 0.5);

		Result result = new Result(testId, testName, grade, totalMark, passed, examineeId);
		return result;
	}

	/**
	 *
	 *
	 * @param userId
	 * @param testId
	 * @param redirectAttributes
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	@RequestMapping(value = "/add-candidate", method = RequestMethod.POST)
	public String addCandidate(@RequestParam(value = "userId") Long userId, @RequestParam(value = "testId") Long testId,
			RedirectAttributes redirectAttributes) {
		Optional<User> userFromDb = userService.findById(userId);

		if (userFromDb.isPresent()) {
			testService.setTestAccess(testId, userId, 1);
		}

		redirectAttributes.addAttribute("testId", testId);
		return "redirect:/questions";
	}
}
