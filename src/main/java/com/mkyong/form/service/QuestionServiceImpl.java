package com.mkyong.form.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkyong.form.dao.QuestionDao;
import com.mkyong.form.model.Question;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

	QuestionDao questionDao;

	@Autowired
	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	@Override
	public Question findById(Integer id) {
		return questionDao.findById(id);
	}

	@Override
	public List<Question> findAll() {
		return questionDao.findAll();
	}

	@Override
	public void saveOrUpdate(Question question) {

		if (question.getId() == null ||  findById(question.getId())==null) {
			questionDao.save(question);
		} else {
			questionDao.update(question);
		}

	}

	@Override
	public void delete(int id) {
		questionDao.deleteQuestion(id);
	}
	
	@Override
	public void deleteQuestion(Question question) {
		questionDao.deleteAnswers(question.getAnswers());
		questionDao.deleteQuestion(question.getId());
	}

}