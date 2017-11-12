package com.mkyong.form.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

	@Autowired
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	@RequestMapping(value = "/quiz", method = RequestMethod.GET)
	public String showAllQuiz(Model model) {

		logger.debug("showQuiz()");
		System.out.println("Number of question "+questionService.findAll().size());
		model.addAttribute("question", questionService.findAll().get(0));
		return "quiz/startQuiz";
	}
	
	@RequestMapping(value = "/quiz", method = RequestMethod.POST)
	public String showResult(Model model) {

		logger.debug("showResult()");
		System.out.println("Number of question "+questionService.findAll().size());
		model.addAttribute("question", questionService.findAll().get(0));
		return "quiz/startQuiz";
	}

}