package com.mkyong.form.model;

public class Answer {
	Integer id;
	String answer;
	Integer questionId;
	boolean rightAnswer;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public boolean isRightAnswer() {
		return rightAnswer;
	}
	public void setRightAnswer(boolean rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
	
}