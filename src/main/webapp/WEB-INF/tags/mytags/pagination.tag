<%@ tag body-content="empty" %> 
<%@ attribute name="current" required="true" %>
<%@ attribute name="count" required="true" %>
<%@ attribute name="limit" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags/mytags" %>
                
<li><a <my:link target="" page="${ current - 1 }" limit="${ limit }" /> aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
<li><a <my:link target="" page="0" limit="${ limit }" /> >1</a></li>
<c:choose>
    <c:when test= "${ count > 5 }">
        <c:choose>
	        <c:when test = "${ current < 4 }">
	            <li><a <my:link target="" page="1" limit="${ limit }" /> >2</a></li>
	            <li><a <my:link target="" page="2" limit="${ limit }" /> >3</a></li>
	            <li><a <my:link target="" page="3" limit="${ limit }" /> >4</a></li>
	            <li><a <my:link target="" page="4" limit="${ limit }" /> >5</a></li>
	            <li><span aria-hidden="true">&hellip;</span></li>
	        </c:when>
	        
	        <c:when test = "${ current > count-4 }">
	            <li><span aria-hidden="true">&hellip;</span></li>
                <li><a <my:link target="" page="${ count - 5 }" limit="${ limit }" /> >${ count - 4}</a></li>
                <li><a <my:link target="" page="${ count - 4 }" limit="${ limit }" /> >${ count - 3 }</a></li>
                <li><a <my:link target="" page="${ count - 3 }" limit="${ limit }" /> >${ count - 2 }</a></li>
                <li><a <my:link target="" page="${ count - 2 }" limit="${ limit }" /> >${ count - 1 }</a></li>
	        </c:when>
	        
	        <c:otherwise>
	            <li><span aria-hidden="true">&hellip;</span></li>
	            <li><a <my:link target="" page="${ current - 2 }" limit="${ limit }" /> >${ current - 1 }</a></li>
                <li><a <my:link target="" page="${ current - 1 }" limit="${ limit }" /> >${ current }</a></li>
                <li><a <my:link target="" page="${ current }" limit="${ limit }" /> >${ current + 1 }</a></li>
	            <li><span aria-hidden="true">&hellip;</span></li>
	        </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test = "${ count > 2}">
        <c:forEach begin="1" end="${ count - 2 }" var="i">
            <li><a  <my:link target="" page="${ i }" limit="${ limit }"/> >${ i + 1 }</a></li>
        </c:forEach>
    </c:when>
    
    
</c:choose>

<c:if test= "${ count > 1 }">
    <li><a <my:link target="" page="${ count - 1 }" limit="${ limit }" /> >${ count }</a></li>
</c:if>

<li><a <my:link target="" page="${ current + 1 }" limit="${ limit }" /> aria-label="Next">
    <span aria-hidden="true">&raquo;</span></a>
</li>