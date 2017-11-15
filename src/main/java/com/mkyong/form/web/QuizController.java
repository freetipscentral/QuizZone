package com.mkyong.form.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mkyong.form.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mkyong.form.model.Answer;
import com.mkyong.form.model.Question;
import com.mkyong.form.service.QuestionService;
import com.mkyong.form.validator.QuestionFormValidator;

@Controller
public class QuizController {

	private final Logger logger = LoggerFactory.getLogger(QuizController.class);

	@Autowired
	QuestionFormValidator questionFormValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(questionFormValidator);
	}

	private QuestionService questionService;

	private QuizService quizService;

	@Autowired
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	@Autowired
	public void setQuizService(QuizService quizService) {
		this.quizService = quizService;
	}

	@RequestMapping(value = "/startQuiz", method = RequestMethod.GET)
	public String showAllQuiz(Model model) {

		logger.debug("showQuiz()");
		List<Question> questions = questionService.findAll();
		System.out.println("Number of question "+questions.size());
		model.addAttribute("question", questions.get(0));
		model.addAttribute("isFirst","true");
		if(questions.size() > 1) {
			model.addAttribute("isLast", "false");
		} else {
			model.addAttribute("isLast", "true");
		}
		return "quiz/startQuiz";
	}

	@RequestMapping(value = "/submitAnswer", method = RequestMethod.POST, params = { "next" })
	public String showNext(@ModelAttribute("question") Question question, Model model) {

		logger.debug("showResult()");
		System.out.println("showNext ..");
		List<Question> questions = questionService.findAll();
		if(question == null) {
			question = questions.get(0);
		}
		int id = question.getId();
		String selectedAnswer = question.getSelectedAnswer();
		int rightAnswer = question.getRightAnswer();
		if(selectedAnswer != null) {
			if(Integer.parseInt(selectedAnswer) == rightAnswer) {
			}
		}

		Question nextQuestion = quizService.findNext(id, questions);
		model.addAttribute("question", nextQuestion);
		boolean isLast = quizService.isLast(nextQuestion.getId(), questions);
		boolean isFirst = quizService.isFirst(nextQuestion.getId(), questions);
		if(isLast) {
			model.addAttribute("isLast", "true");
		} else {
			model.addAttribute("isLast", "false");
		}
		if(isFirst) {
			model.addAttribute("isFirst", "true");
		} else {
			model.addAttribute("isFirst", "false");
		}
		System.out.println("Selected answer "+question.getSelectedAnswer());
		return "quiz/startQuiz";
	}

	@RequestMapping(value = "/submitAnswer", method = RequestMethod.POST, params = { "submit" })
	public String showResult(@ModelAttribute("question") Question question, Model model) {

		logger.debug("showResult()");
		System.out.println("showResult ..");
		System.out.println("Selected answer "+question.getSelectedAnswer());
		return "quiz/startQuiz";
	}

}