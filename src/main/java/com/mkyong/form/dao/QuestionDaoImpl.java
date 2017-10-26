package com.mkyong.form.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.mkyong.form.model.Answer;
import com.mkyong.form.model.Question;

@Repository
public class QuestionDaoImpl implements QuestionDao {

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws DataAccessException {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public Question findById(Integer id) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		String sql = "SELECT questions.id id, questions.questionText questionText, questions.rightAnswer rightAnswer, "
				+ "answers.id answerId, answers.answer answer, answers.questionId questionId "
				+ "FROM questions join answers on questions.id = answers.questionId and id="+id;

		List<Question> result = null;
		try {
			//result = namedParameterJdbcTemplate.queryForObject(sql, params, new QuestionMapper());
			result = namedParameterJdbcTemplate.query(sql, new QuestionExtractor());
		} catch (EmptyResultDataAccessException e) {
			// do nothing, return null
		}

		/*
		 * User result = namedParameterJdbcTemplate.queryForObject( sql, params,
		 * new BeanPropertyRowMapper<User>());
		 */

		return result.get(0);
	}

	@Override
	public List<Question> findAll() {

		String sql = "SELECT questions.id id, questions.questionText questionText, questions.rightAnswer rightAnswer, "
				+ "answers.id answerId, answers.answer answer, answers.questionId questionId "
				+ "FROM questions join answers on questions.id = answers.questionId ";
		//List<Question> result = namedParameterJdbcTemplate.query(sql, new QuestionMapper());

		//String sql = "SELECT ID, NAME, AGE FROM EMPLOYEE";        
		List<Question> result = namedParameterJdbcTemplate.query(sql, new QuestionExtractor());

		return result;
	}	

	private class QuestionExtractor implements ResultSetExtractor<List>{

		public List<Question> extractData(ResultSet rs) throws SQLException, DataAccessException {
			Map<Integer, Question> map = new HashMap<Integer, Question>();
			Question question = null;
			List<Answer> answers = null;
			while (rs.next()) {
				Integer id = rs.getInt("ID");
				question = map.get(id);
				if(question == null){
					question = new Question();
					question.setId(id);
					question.setQuestionText(rs.getString("questionText"));
					question.setRightAnswer(rs.getInt("rightAnswer"));
					answers = new ArrayList<>();
					question.setAnswers(answers);
				} 

				Answer answer = new Answer();
				answer.setId(rs.getInt("answerId"));
				answer.setQuestionId(question.getId());
				answer.setAnswer(rs.getString("answer"));
				if(question.getAnswers() == null) {
					question.setAnswers(new ArrayList<Answer>());
				}
				question.getAnswers().add(answer);
				map.put(id, question);
			}
			return new ArrayList<Question>(map.values());
		}
	}

	@Override
	public void save(Question question) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		String sql = "INSERT INTO QUESTIONS(QUESTIONTEXT, RIGHTANSWER) "
				+ "VALUES ( :questionText, :rightAnswer)";

		KeyHolder answerKeyHolder = new GeneratedKeyHolder();
		String answerSql = "INSERT INTO ANSWERS(ANSWER, QUESTIONID) "
				+ "VALUES ( :answer, :questionId )";

		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(question), keyHolder);
		question.setId(keyHolder.getKey().intValue());
		List<Answer> answers = question.getAnswers();
		for(Answer answer : answers) {
			answer.setQuestionId(question.getId());
			namedParameterJdbcTemplate.update(answerSql, getSqlParameterByAnswerModel(answer), answerKeyHolder);
			answer.setId(answerKeyHolder.getKey().intValue());
		}
	}

	public int[] batchAddUsingJdbcTemplate(final List<Answer> answers) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(answers.toArray());
		int[] updateCounts = namedParameterJdbcTemplate.batchUpdate(
				"UPDATE Answers SET answer = :answer WHERE id = :id", batch);
		return updateCounts;
	}

	@Override
	public void update(Question question) {

		String sql = "UPDATE Questions SET QUESTIONTEXT=:questionText, RIGHTANSWER=:rightAnswer WHERE id=:id";
		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(question));
		int[] updates = batchUpdateUsingJdbcTemplate(question.getAnswers());
	}

	public int[] batchUpdateUsingJdbcTemplate(final List<Answer> answers) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(answers.toArray());
		int[] updateCounts = namedParameterJdbcTemplate.batchUpdate(
				"UPDATE Answers SET answer = :answer WHERE id = :id", batch);
		return updateCounts;
	}

	@Override
	public void deleteQuestion(Integer id) {

		String sql = "DELETE FROM QUESTIONS WHERE id= :id";
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
	}
	
	@Override
	public void deleteAnswers(List<Answer> answers) {
		if(answers != null) {
			for(Answer answer : answers) {
				String sql = "DELETE FROM ANSWERS WHERE id= :id";
				namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", answer.getId()));
			}
		}
	}

	private SqlParameterSource getSqlParameterByModel(Question question) {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", question.getId());
		paramSource.addValue("questionText", question.getQuestionText());
		paramSource.addValue("rightAnswer", question.getRightAnswer());
		return paramSource;
	}

	private SqlParameterSource getSqlParameterByAnswerModel(Answer answer) {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("answer", answer.getAnswer());
		paramSource.addValue("questionId", answer.getQuestionId());
		return paramSource;
	}

	private static final class QuestionMapper implements RowMapper<Question> {

		public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
			Question question = new Question();
			question.setId(rs.getInt("id"));
			question.setQuestionText(rs.getString("questionText"));
			question.setRightAnswer(rs.getInt("rightAnswer"));
			return question;
		}
	}

	private static List<String> convertDelimitedStringToList(String delimitedString) {

		List<String> result = new ArrayList<String>();

		if (!StringUtils.isEmpty(delimitedString)) {
			result = Arrays.asList(StringUtils.delimitedListToStringArray(delimitedString, ","));
		}
		return result;
	}

	private String convertListToDelimitedString(List<String> list) {

		String result = "";
		if (list != null) {
			result = StringUtils.arrayToCommaDelimitedString(list.toArray());
		}
		return result;
	}
}