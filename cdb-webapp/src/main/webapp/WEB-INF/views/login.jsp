<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="header.title" text="Computer Database" /></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap -->
        <link
            href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
            rel="stylesheet" media="screen">
        <link
            href="${pageContext.request.contextPath}/resources/css/font-awesome.css"
            rel="stylesheet" media="screen">
        <link href="${pageContext.request.contextPath}/resources/css/main.css"
            rel="stylesheet" media="screen">
    </head>
    <body>
        <header class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <a class="navbar-brand"
                    href="${pageContext.request.contextPath}/computer">Application -
                    <spring:message code="header.title" text="Computer Database" />
                </a>
                <span class="navbar-brand navbar-right">
                <a href="${pageContext.request.contextPath}/login?lang=fr">
                    <img src="${pageContext.request.contextPath}/resources/images/france_flag.png" alt="FR" style="width:30px;"/>
                </a> / 
                <a href="${pageContext.request.contextPath}/login?lang=en">
                    <img src="${pageContext.request.contextPath}/resources/images/uk_flag.png" alt="EN" style="width:30px;"/>
                </a></span>
            </div>
        </header>
        <form id="login_form" action="${loginUrl}" method="post">
            <c:if test="${param.error != null}">
                <p>Invalid username and password.</p>
            </c:if>
            <c:if test="${param.logout != null}">
                <p>You have been logged out.</p>
            </c:if>
            <p>
                <label for="username"><spring:message code="field.username" /></label>
                <input class="form-control" type="text" id="username" name="username" />
            </p>
            <p>
                <label for="password"><spring:message code="field.password" /></label>
                <input class="form-control" type="password" id="password"
                    name="password" />
            </p>
            <input type="hidden" name="${_csrf.parameterName}"
                value="${_csrf.token}" class="form-control" />
            <button type="submit" class="btn">
                <spring:message code="confirm.login" />
            </button>
        </form>
        <script
            src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
        <script
            src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <script
            src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script>
        <script
            src="${pageContext.request.contextPath}/resources/js/computerFormValidation.js"></script>
    </body>
</html>