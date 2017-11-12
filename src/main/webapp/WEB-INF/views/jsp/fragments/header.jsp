<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
<title>Spring MVC Form Handling Example</title>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
<script src="<spring:url value="/resources/core/js/jquery.js" />"></script>
<script src="<spring:url value="/resources/core/js/question.js" />"></script>
</head>

<spring:url value="/" var="urlHome" />
<spring:url value="/quiz" var="urlQuiz" />
<spring:url value="/questions" var="urlAllQuestions" />
<spring:url value="/users/add" var="urlAddUser" />
<spring:url value="/questions/add" var="urlAddQuestion" />

<nav class="navbar navbar-inverse ">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${urlHome}">Spring MVC Form</a>
		</div>
		<div id="navbar">
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlQuiz}">Quiz</a></li>
				<li class="active"><a href="${urlAddUser}">Add User</a></li>
				<li class="active"><a href="${urlAddQuestion}">Add Question</a></li>
				<li class="active"><a href="${urlAllQuestions}">Question List</a></li>
			</ul>
		</div>
	</div>
</nav>