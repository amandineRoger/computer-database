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
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
             <a href="home" class="navbar-brand"><spring:message code="header.home"/></a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${ computer.id }
                    </div>
                    <h1><spring:message code="edit.title"/></h1>

                    <form action="edit" method="POST">
                        <input id="id" type="hidden" value="${computer.id}" name="computerId" />
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" class="form-control" />
                        
                        <fieldset>
                            <div class="form-group ">
                                <label class="control-label" for="computerName"><spring:message code="computer.name"/>
                                    <span style="color: red;">*</span>
                                </label>
                                <input type="text" class="form-control" id="computerName" name="computerName" value="${ computer.name }">
                                <span class="help-block hide" id="errorComputerName"><spring:message code="error.name"/></span>
                            </div>
                           <div class="form-group ">
                                <label class="control-label" for="introduced"><spring:message code="computer.introduced"/></label> 
                                <input type="date" class="form-control" id="introduced" name="introduced" value="${ computer.introduced }">
                                <span class="help-block hide" id="errorIntroduced" ><spring:message code="error.date"/></span>
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="discontinued"><spring:message code="computer.discontinued"/></label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" value="${ computer.discontinued }">
                                <span class="help-block hide" id="errorDiscontinued" ><spring:message code="error.date"/></span>
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" name="companyId" >
                                    <c:forEach items="${ companiesList }" var="company">
                                        <option value="${ company.id }" ${ company.id == computer.companyId ? "selected" : "" }>
                                            <c:out value="${ company.name }"/>
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="confirm.edit"/>" id="editButton" class="btn btn-primary" disabled="disabled" >
                            <spring:message code="confirm.or"/>
                             <my:link target="${ pageContext.request.contextPath }/home" baliseClass="btn btn-default"><spring:message code="confirm.cancel"/></my:link>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    
    <script src="resources/js/jquery.min.js"></script>
    <script src="resources/js/bootstrap.min.js"></script>
    <script src="resources/js/addValidation.js"></script>
    
</body>
</html>