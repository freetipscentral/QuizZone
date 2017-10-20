package com.mkyong.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mkyong.form.model.Question;
import com.mkyong.form.service.QuestionService;

//http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html#validation-mvc-configuring
@Component
public class QuestionFormValidator implements Validator {

	@Autowired
	QuestionService questionService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Question.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Question question = (Question) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "questionText", "NotEmpty.questionForm.questionText");
	}

}