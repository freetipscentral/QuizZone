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

	<spring:url value="/questions" var="questionActionUrl" />

	<form:form class="form-horizontal" method="post" modelAttribute="questionForm" action="${questionActionUrl}">

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

		<spring:bind path="rightAnswer">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Right Answer</label>
				<div class="col-sm-10">
					<form:input path="rightAnswer" class="form-control" id="rightAnswer" placeholder="Right Answer" />
					<form:errors path="rightAnswer" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" name="addAnswer" class="btn-lg btn-primary pull-right">Add Answer</button>
				<c:choose>
					<c:when test="${questionForm['new']}">
						<button type="submit" class="btn-lg btn-primary pull-right">Add</button>
					</c:when>
					<c:otherwise>
						<c:forEach var="ans" items="${questionForm.answers}" varStatus="status">
							<div class="col-sm-10">
								<input name="answers[${status.index}].answer" value="${ans.answer}"/>
								<button type="button" value="${ans.id}" id="deleteAnswer-${ans.id}" class="btn-lg btn-danger pull-right">Delete</button>
							</div>
						</c:forEach>									
						<button type="submit" class="btn-lg btn-primary pull-right">Update</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
</div>

<jsp:include page="../fragments/footer.jsp" />

</body>
</html>