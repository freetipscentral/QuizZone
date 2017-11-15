<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<div class="container">

	<c:if test="${not empty msg}">
		<div class="alert alert-${css} alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>

	<h1>Question Detail</h1>
	<br />
	<spring:url value="/submitAnswer" var="userActionUrl" />

	<form:form class="form-horizontal" method="post" modelAttribute="question" action="${userActionUrl}">

	<div class="row">
		<label class="col-sm-2">ID</label>
		<div class="col-sm-10">${question.id}</div>
		<input type="hidden" name="id" value="${question.id}"/>
	</div>

	<div class="row">
		<label class="col-sm-2">Question Text</label>
		<div class="col-sm-10">${question.questionText}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Right Answer</label>
		<div class="col-sm-10">${question.rightAnswer}</div>
		<input type="hidden" name="rightAnswer" value="${question.rightAnswer}"/>
	</div>

	<c:forEach var="ans" items="${question.answers}" varStatus="status">
		<div class="col-sm-10">
			<input name="answers[${status.index}].answer" value="${ans.answer}"/>

			<input type="radio"  name="selectedAnswer" class="form-control" value="${ans.id}" />
		</div>
	</c:forEach>
</div>
<c:if test = "${isFirst == 'false'}">
	<button type="submit" class="btn-lg btn-primary " name="previous" value="Previous">Previous</button>
</c:if>

<c:if test = "${isLast == 'true'}">
	<button type="submit" class="btn-lg btn-primary " name="submit" value="Submit">Submit</button>
</c:if>
<c:if test = "${isLast == 'false'}">
	<button type="submit" class="btn-lg btn-primary " name="next" value="Next">Next</button>
</c:if>
</form:form>
<jsp:include page="../fragments/footer.jsp" />

</body>
</html>