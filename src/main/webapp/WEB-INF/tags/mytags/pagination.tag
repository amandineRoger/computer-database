<%@ tag body-content="empty" %> 
<%@ attribute name="current" required="true" %>
<%@ attribute name="count" required="true" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
                
<li><a href="?page=${ current }&prev=true" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
<li><a href="?page=0">1</a></li>
<c:choose>
    <c:when test= "${ count > 5 }">
        <c:choose>
	        <c:when test = "${ current < 4 }">
	            <li><a href="?page=1">2</a></li>
	            <li><a href="?page=2">3</a></li>
	            <li><a href="?page=3">4</a></li>
	            <li><a href="?page=4">5</a></li>
	            <li><span aria-hidden="true">&hellip;</span></li>
	        </c:when>
	        
	        <c:when test = "${ current > count-4 }">
	            <li><span aria-hidden="true">&hellip;</span></li>
	            <li><a href="?page=${ count - 5 }"><c:out value="${ count - 4}"/></a></li>
	            <li><a href="?page=${ count - 4 }"><c:out value="${ count - 3}"/></a></li>
	            <li><a href="?page=${ count - 3 }"><c:out value="${ count - 2}"/></a></li>
	            <li><a href="?page=${ count - 2 }"><c:out value="${ count - 1}"/></a></li>
	        </c:when>
	        
	        <c:otherwise>
	            <li><span aria-hidden="true">&hellip;</span></li>
	            <li><a href="?page=${ current - 2 }"><c:out value="${ current - 1}"/></a></li>
	            <li><a href="?page=${ current - 1 }"><c:out value="${ current }"/></a></li>
	            <li><a href="?page=${ current }"><c:out value="${ current + 1}"/></a></li>
	            <li><span aria-hidden="true">&hellip;</span></li>
	        </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test = "${ count > 2}">
        <c:forEach begin="1" end="${ count - 2 }" var="i">
            <li><a href="?page=${ i }"><c:out value="${ i + 1 }"/></a></li>
        </c:forEach>
    </c:when>
    
    
</c:choose>

<c:if test= "${ count > 1 }">
    <li><a href="?page=${ count - 1}"><c:out value="${ count }"/></a></li>
</c:if>

<li><a href="?page=${ current }&prev=false" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>