package com.mkyong.form.dao;

import java.util.List;

import com.mkyong.form.model.Question;

public interface QuestionDao {

	Question findById(Integer id);

	List<Question> findAll();

	void save(Question question);

	void update(Question question);

	void delete(Integer id);

}