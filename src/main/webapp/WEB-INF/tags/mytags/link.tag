<%@ tag body-content="empty" %>
<%@ attribute name="target" required="true" %> 
<%@ attribute name="limit" required="false" %>
<%@ attribute name="currentPageNumber" required="false" %>
<%@ attribute name="search" required="false" %>
<%@ attribute name="order" required="false" %>
<%@ attribute name="asc" required="false" %>
<%@ attribute name="id" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

href="${ target }?limit=${ limit }&page=${ currentPageNumber }&search=${ search }&order=${ order }&asc=${ asc }&id=${ id }"
