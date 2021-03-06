<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<div class="container">

	<c:choose>
		<c:when test="${questionForm['new']}">
			<h1>Add Question</h1>
		</c:when>
		<c:otherwise>
			<h1>Update Question</h1>
		</c:otherwise>
	</c:choose>
	<br />

	<spring:url value="/saveQuestions" var="questionActionUrl" />

<%-- 	<form:form class="form-horizontal" method="post" modelAttribute="questionForm" action="${questionActionUrl}"> --%>
	<form:form class="form-horizontal" method="post" modelAttribute="questionForm">

		<form:hidden path="id" />

		<spring:bind path="questionText">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Question</label>
				<div class="col-sm-10">
					<form:input path="questionText" type="text" class="form-control " id="questionText" placeholder="Enter Question" />
					<form:errors path="questionText" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<%-- <spring:bind path="rightAnswer">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Right Answer</label>
				<div class="col-sm-10">
					<form:input path="rightAnswer" class="form-control" id="rightAnswer" placeholder="Right Answer" />
					<form:errors path="rightAnswer" class="control-label" />
				</div>
			</div>
		</spring:bind> --%>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				
				<c:choose>
					<c:when test="${questionForm['new']}">
						
						<c:forEach var="ans" items="${questionForm.answers}" varStatus="status">
							<div class="col-sm-10">
								<input name="answers[${status.index}].answer" value="${ans.answer}"/>
								
								<%-- <form:radiobutton path="rightAnswer" class="form-control" value="${status.index}" /> --%>
								<input type="radio"  name="rightAnswer" value="${status.index}"/>
								<button type="button" value="${ans.id}" id="deleteAnswer-${ans.id}" class="btn-lg btn-danger pull-right">Delete</button>
							</div>
						</c:forEach>
						<br/>
						<button type="submit" class="btn-lg btn-primary pull-right" name="add" value="Add">Add</button>
						<button type="submit" class="btn-lg btn-primary pull-right" name="saveQuestion" value="saveQuestion">Save Question</button>
					</c:when>
					<c:otherwise>
						<c:forEach var="ans" items="${questionForm.answers}" varStatus="status">
							<div class="col-sm-10">
								<input name="answers[${status.index}].answer" value="${ans.answer}"/>
								<input type="hidden" name="answers[${status.index}].id" value="${ans.id}"/>
								<form:radiobutton path="rightAnswer" class="form-control" value="${ans.id}" />
								<button type="button" value="${ans.id}" id="deleteAnswer-${ans.id}" class="btn-lg btn-danger pull-right">Delete</button>
							</div>
						</c:forEach>									
						<button type="submit" class="btn-lg btn-primary pull-right" name="update" value="Update">Update</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
</div>

<jsp:include page="../fragments/footer.jsp" />

</body>
</html>