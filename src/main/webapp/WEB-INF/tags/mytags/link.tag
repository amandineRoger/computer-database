<%@ tag body-content="scriptless" language="java" pageEncoding="UTF-8"%>
<%@ attribute name="target" required="true" %> 
<%@ attribute name="limit" required="false" %>
<%@ attribute name="currentPageNumber" required="false" %>
<%@ attribute name="search" required="false" %>
<%@ attribute name="order" required="false" %>
<%@ attribute name="asc" required="false" %>
<%@ attribute name="id" required="false" %>
<%@ attribute name="baliseClass" required="false" %>
<%@ attribute name="baliseId" required="false" %>
<%@ attribute name="ariaLabel" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<a href="${ target }?limit=${ limit }&page=${ currentPageNumber }&search=${ search }&order=${ order }&asc=${ asc }&id=${ id }" class="${ baliseClass }" id="${ baliseId }" aria-label="${ ariaLabel }">
    <jsp:doBody></jsp:doBody>
</a>