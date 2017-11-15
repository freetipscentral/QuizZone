package com.mkyong.form.service;

import com.mkyong.form.dao.QuestionDao;
import com.mkyong.form.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("quizService")
public class QuizServiceImpl implements QuizService {

	QuestionDao questionDao;

	@Autowired
	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	@Override
	public Question showQuestionbyId(Integer id) {
		return null;
	}

	@Override
	public void showNextQuestion(Question question) {

	}

	@Override
	public void showResult() {

	}

	@Override
	public boolean isFirst(int questionId, List<Question> questions) {
		for(Question question : questions) {
			if(question.getId() == questionId && questions.get(0).equals(question)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isLast(int questionId, List<Question> questions) {
		int size = questions.size();
		for(Question question : questions) {
			if(question.getId() == questionId && questions.get(size - 1).equals(question)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Question findNext(Integer id, List<Question> questions) {
		for(Question question : questions) {
			if(question.getId() == id) {
				int position = questions.indexOf(question);
				return questions.get(position + 1);
			}
		}
		return null;
	}
}