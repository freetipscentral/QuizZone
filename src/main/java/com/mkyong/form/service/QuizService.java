package com.mkyong.form.service;

import com.mkyong.form.model.Question;

import java.util.List;

public interface QuizService {

	Question showQuestionbyId(Integer id);

	void showNextQuestion(Question question);

	void showResult();

	boolean isFirst(int questionId, List<Question> questions);

	boolean isLast(int questionId, List<Question> questions);

	Question findNext(Integer id, List<Question> questions);
}