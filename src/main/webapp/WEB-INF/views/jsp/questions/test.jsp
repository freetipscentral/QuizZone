<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

	<h1>Question Detail</h1>
	
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
<jsp:include page="../fragments/footer.jsp" />

</body>
</html>