<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags/mytags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="header.title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
            <a href="home" class="navbar-brand"><spring:message code="header.home"/></a>
            <a href="home?lang=fr" class="navbar-brand navbar-right">
                <img alt="French" src="${pageContext.request.contextPath}/resources/flag-fr.png" id="flag-fr">
            </a>
             <a href="home?lang=en" class="navbar-brand navbar-right">
                <img alt="English" src="${pageContext.request.contextPath}/resources/flag-en.png" id="flag-en">
            </a>
            
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value=" ${computersCount}" />
				<spring:message code="index.nbComputers"/>
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder='<spring:message code="placeholder.search"/>' /> <input
							type="submit" id="searchsubmit" value='<spring:message code="index.filter"/>'
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					   <my:link baliseClass="btn btn-success" target="${ pageContext.request.contextPath }/newComputer" baliseId="addComputer"> 
					   <spring:message code="index.add"/>
					</my:link>
					<a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode('<spring:message code="index.edit"/>','<spring:message code="index.editOn"/>' );"><spring:message code="index.edit"/></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="delete" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->
						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected('<spring:message code="confirm.delete"/>');"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>
						  <my:link target="" currentPageNumber="${ pageNumber }" search="${ search }" limit="${ limit }" order="name" asc="${ asc }"><spring:message code="computer.name"/></my:link>
                        </th>
						<th> 
						  <my:link target="" currentPageNumber="${ pageNumber }" search="${ search }" limit="${ limit }" order="introduced" asc="${ asc }"><spring:message code="computer.introduced"/></my:link>
						</th>
						<!-- Table header for Discontinued Date -->
						<th>
						  <my:link target="" currentPageNumber="${ pageNumber }" search="${ search }" limit="${ limit }" order="discontinued" asc="${ asc }"><spring:message code="computer.discontinued"/></my:link>
						</th>
						<!-- Table header for Company -->
						<th>
						  <my:link target="" currentPageNumber="${ pageNumber }" search="${ search }" limit="${ limit }" order="company_id" asc="${ asc }"><spring:message code="computer.company"/></my:link>
						</th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${ page.list }" var="computer">
						<tr>

							<td class="editMode"><input type="checkbox" id="${ computer.name }_id" name="cb"
								class="cb" value="${ computer.id }"></td>
							<td>
							 <my:link target="http://localhost:8080/computer-database/edit" baliseId="${ computer.name }_name" id="${ computer.id }"><c:out value="${ computer.name }" /></my:link>
							</td>
							<td><c:out value="${ computer.introduced }" /></td>
							<td><c:out value="${ computer.discontinued }" /></td>
							<td><c:out value="${ computer.companyName }" /></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<my:pagination count="${ page.nbPages }" current="${ pageNumber }"/>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
			
				 <my:link target="" limit="10" baliseClass="btn btn-default">10</my:link>
				<my:link target="" limit="50" baliseClass="btn btn-default" >50</my:link>
				<my:link target="" limit="100" baliseClass="btn btn-default">100</my:link>
			</div>
	   </div>
	</footer>
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/dashboard.js"></script>

</body>
</html>