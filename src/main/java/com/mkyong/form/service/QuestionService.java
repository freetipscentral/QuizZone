package com.mkyong.form.service;

import java.util.List;

import com.mkyong.form.model.Question;

public interface QuestionService {

	Question findById(Integer id);
	
	List<Question> findAll();

	void saveOrUpdate(Question question);
	
	void delete(int id);
	
	void deleteQuestion(Question question);

}