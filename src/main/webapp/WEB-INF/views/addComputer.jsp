<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand"
				href="${ pageContext.request.contextPath }/home"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form action="newComputer" method="POST">
						<fieldset>
							<div class="form-group ">
								<label class="control-label" for="computerName">Computer name
								    <span style="color: red;">*</span>
								</label>
								<input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name">
								<span class="help-block sr-only" id="errorComputerName" >You must specify a name !</span>
                            </div>
							<div class="form-group ">
								<label class="control-label" for="introduced">Introduced date</label> 
								<input type="date" class="form-control" id="introduced"	name="introduced" placeholder="Introduced date">
                                <span class="help-block sr-only" id="errorIntroduced" >Date format must be like YYYY-MM-DD</span>
							</div>
							<div class="form-group">
								<label class="control-label" for="discontinued">Discontinued date</label>
								<input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date">
                                <span class="help-block sr-only" id="errorDiscontinued" >Date format must be like YYYY-MM-DD</span>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label>
								<select class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${ companiesList }" var="company">
										<option value="${ company.id }"><c:out
												value="${ company.name }" /></option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" id="submitButton" class="btn btn-primary" disabled="disabled" >
							or <a href="${ pageContext.request.contextPath }/home" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/addValidation.js"></script>
	
</body>
</html>