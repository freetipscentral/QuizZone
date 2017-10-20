<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<body>

	<div class="container">

		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>

		<h1>All Questions</h1>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>#ID</th>
					<th>Question Text</th>
					<th>Right Answer</th>
					<th>Answers</th>
					<th>Action</th>
				</tr>
			</thead>

			<c:forEach var="question" items="${questions}">
				<tr>
					<td>
						${question.id}
					</td>
					<td>${question.questionText}</td>
					<td>${question.rightAnswer}</td>
					<td>
						<c:forEach var="answer" items="${question.answers}">
							<div>
								${answer.id} - ${answer.questionId} - ${answer.answer}
							</div>
						</c:forEach>
					</td>
					<td>
						<spring:url value="/questions/${question.id}" var="questionUrl" />
						<spring:url value="/questions/${question.id}/delete" var="deleteUrl" /> 
						<spring:url value="/questions/${question.id}/update" var="updateUrl" />

						<button class="btn btn-info" onclick="location.href='${questionUrl}'">Query</button>
						<button class="btn btn-primary" onclick="location.href='${updateUrl}'">Update</button>
						<button class="btn btn-danger" onclick="this.disabled=true;post('${deleteUrl}')">Delete</button>
					</td>
				</tr>
			</c:forEach>
		</table>

	</div>

	<jsp:include page="../fragments/footer.jsp" />

</body>
</html>