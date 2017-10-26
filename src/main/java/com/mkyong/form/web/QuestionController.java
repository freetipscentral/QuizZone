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
//import javax.validation.Valid;

//http://www.tikalk.com/redirectattributes-new-feature-spring-mvc-31/
//https://en.wikipedia.org/wiki/Post/Redirect/Get
//http://www.oschina.net/translate/spring-mvc-flash-attribute-example
@Controller
public class QuestionController {

	private final Logger logger = LoggerFactory.getLogger(QuestionController.class);

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

	// list page
	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public String showAllQuestions(Model model) {

		logger.debug("showAllQuestions()");
		model.addAttribute("questions", questionService.findAll());
		return "questions/test";

	}

	// save or update question
	@RequestMapping(value = "/questions", method = RequestMethod.POST)
	public String saveOrUpdateQuestion(@ModelAttribute("questionForm") @Validated Question question,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateQuestion() : {}", question);

		if (result.hasErrors()) {
			populateDefaultModel(model);
			return "questions/questionform";
		} else {

			redirectAttributes.addFlashAttribute("css", "success");
			if(question.isNew()){
				redirectAttributes.addFlashAttribute("msg", "Question added successfully!");
			}else{
				redirectAttributes.addFlashAttribute("msg", "Question updated successfully!");
			}
			
			questionService.saveOrUpdate(question);
			
			return "redirect:/questions/" + question.getId();
		}
	}

	// show add question form
	@RequestMapping(value = "/questions/add", method = RequestMethod.GET)
	public String showAddQuestionsForm(Model model) {
		logger.debug("showAddQuestionForm()");

		Question question = new Question();

		// set default value
		question.setQuestionText("mkyong123");
		question.setRightAnswer(2);

		model.addAttribute("questionForm", question);

		populateDefaultModel(model);

		return "questions/questionform";
	}
	
	
	
	// show update form
	@RequestMapping(value = "/questions/{id}/update", method = RequestMethod.GET)
	public String showUpdateQuestionForm(@PathVariable("id") int id, Model model) {

		logger.debug("showUpdateQuestionForm() : {}", id);

		Question question = questionService.findById(id);
		System.out.println("Size :"+question.getAnswers().size());
		model.addAttribute("questionForm", question);
		
		populateDefaultModel(model);
		
		return "questions/questionform";

	}

	// show add question form
	@RequestMapping(value = "/question/addAnswer", method = RequestMethod.POST)
	public String addAnswer(@ModelAttribute("questionForm") Question question) {
		logger.debug("addAnswer()");

		Answer answer = new Answer();
		question.getAnswers().add(answer);

		return "questions/questionform";
	}
	
	//@RequestMapping(value = "/saveQuestions", method = RequestMethod.POST)
	@RequestMapping(params = "update", method = RequestMethod.POST)
	public String updateQuestions(@ModelAttribute("questionForm") Question question) {
		
		questionService.saveOrUpdate(question);
		return "redirect:/questions/";
	}
	
	@RequestMapping(params = "saveQuestion", method = RequestMethod.POST)
	public String saveQuestion(@ModelAttribute("questionForm") Question question) {
		
		questionService.saveOrUpdate(question);
		return "redirect:/questions/";
	}
	
	@RequestMapping(params = "add", method = RequestMethod.POST)
	public String addQuestions(@ModelAttribute("questionForm") Question question) {
		Answer answer = new Answer();
		List<Answer> answers = question.getAnswers();
		if(answers == null) {
			answers = new ArrayList<Answer>();
			question.setAnswers(answers);
		}
		answers.add(answer);
		return "questions/questionform";
	}
	
	// delete question
	@RequestMapping(value = "/questions/{id}/delete", method = RequestMethod.POST)
	public String deleteQuestion(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {

		logger.debug("deleteQuestion() : {}", id);

		questionService.delete(id);
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Question is deleted!");
		
		return "redirect:/questions";

	}
	
	// delete question
	@RequestMapping(value = "/questions/{id}/delete", method = RequestMethod.GET)
	public String deleteQuestion(@PathVariable("id") int id, @ModelAttribute("questionForm") Question question) {

		logger.debug("deleteQuestion() : {}", id);
		Question questionToDelete = questionService.findById(id);
		questionService.deleteQuestion(questionToDelete); 
		System.out.println("id to delete : "+id);
		
		return "redirect:/questions";
	}

	// show question
	@RequestMapping(value = "/questions/{id}", method = RequestMethod.GET)
	public String showQuestion(@PathVariable("id") int id, Model model) {

		logger.debug("showQuestion() id: {}", id);

		Question question = questionService.findById(id);
		if (question == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Question not found");
		}
		model.addAttribute("question", question);

		return "questions/show";

	}

	private void populateDefaultModel(Model model) {

		List<String> frameworksList = new ArrayList<String>();
		frameworksList.add("Spring MVC");
		frameworksList.add("Struts 2");
		frameworksList.add("JSF 2");
		frameworksList.add("GWT");
		frameworksList.add("Play");
		frameworksList.add("Apache Wicket");
		model.addAttribute("frameworkList", frameworksList);

		Map<String, String> skill = new LinkedHashMap<String, String>();
		skill.put("Hibernate", "Hibernate");
		skill.put("Spring", "Spring");
		skill.put("Struts", "Struts");
		skill.put("Groovy", "Groovy");
		skill.put("Grails", "Grails");
		model.addAttribute("javaSkillList", skill);

		List<Integer> numbers = new ArrayList<Integer>();
		numbers.add(1);
		numbers.add(2);
		numbers.add(3);
		numbers.add(4);
		numbers.add(5);
		model.addAttribute("numberList", numbers);

		Map<String, String> country = new LinkedHashMap<String, String>();
		country.put("US", "United Stated");
		country.put("CN", "China");
		country.put("SG", "Singapore");
		country.put("MY", "Malaysia");
		model.addAttribute("countryList", country);

	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ModelAndView handleEmptyData(HttpServletRequest req, Exception ex) {

		logger.debug("handleEmptyData()");
		logger.error("Request: {}, error ", req.getRequestURL(), ex);

		ModelAndView model = new ModelAndView();
		model.setViewName("question/show");
		model.addObject("msg", "question not found");

		return model;

	}

}