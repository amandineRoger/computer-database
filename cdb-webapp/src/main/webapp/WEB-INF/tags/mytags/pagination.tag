<%@ tag body-content="empty" %> 
<%@ attribute name="current" required="true" %>
<%@ attribute name="count" required="true" %>
<%@ attribute name="limit" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags/mytags" %>
                
<!-- hide button "previous" when on first page -->
<c:if test="${ current != 0 }">
    <li><my:link target="" currentPageNumber="${ current - 1 }" limit="${ limit }" ariaLabel="Previous"><span aria-hidden="true">&laquo;</span></my:link></li>
</c:if>
<!-- always display button for page 1 -->
<li><my:link target="" currentPageNumber="0" limit="${ limit }">1</my:link></li>
<c:choose>
    <c:when test= "${ count > 5 }">
    <!-- when there is more than 5 pages -->
        <c:choose>
	        <c:when test = "${ current < 4 }">
	        <!-- beginning (page 1 to 5) -->
	            <li><my:link target="" currentPageNumber="1" limit="${ limit }">2</my:link></li>
	            <li><my:link target="" currentPageNumber="2" limit="${ limit }">3</my:link></li>
	            <li><my:link target="" currentPageNumber="3" limit="${ limit }">4</my:link></li>
	            <li><my:link target="" currentPageNumber="4" limit="${ limit }">5</my:link></li>
	            <li><span aria-hidden="true">&hellip;</span></li>
	        </c:when>
	        <c:when test = "${ current > count-4 }">
	        <!-- page from 5 to count-4 grouped by three, centered on current page-->
	            <li><span aria-hidden="true">&hellip;</span></li>
                <li><my:link target="" currentPageNumber="${ count - 5 }" limit="${ limit }">${ count - 4 }</my:link></li>
                <li><my:link target="" currentPageNumber="${ count - 4 }" limit="${ limit }">${ count - 3 }</my:link></li>
                <li><my:link target="" currentPageNumber="${ count - 3 }" limit="${ limit }">${ count - 2 }</my:link></li>
                <li><my:link target="" currentPageNumber="${ count - 2 }" limit="${ limit }">${ count - 1 }</my:link></li>
	        </c:when>
	        <c:otherwise>
	        <!-- end (page count-4 to count -1) -->
	            <li><span aria-hidden="true">&hellip;</span></li>
	            <li><my:link target="" currentPageNumber="${ current - 1 }" limit="${ limit }">${ current }</my:link></li>
                <li><my:link target="" currentPageNumber="${ current }" limit="${ limit }">${ current + 1}</my:link></li>
                <li><my:link target="" currentPageNumber="${ current + 1 }" limit="${ limit }">${ current + 2 }</my:link></li>
	            <li><span aria-hidden="true">&hellip;</span></li>
	        </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test = "${ count > 2}">
        <!-- when there is 2 to 5 pages -->
        <c:forEach begin="1" end="${ count - 2 }" var="i">
            <li><my:link target="" currentPageNumber="${ i }" limit="${ limit }">${ i + 1 }</my:link></li>
        </c:forEach>
    </c:when>    
</c:choose>

<!-- display the last page if there is more than one -->
<c:if test= "${ count > 1 }">
    <li><my:link target="" currentPageNumber="${ count - 1 }" limit="${ limit }">${ count }</my:link></li>
</c:if>
<!-- hide button "next" when on last page -->
<c:if test="${ current != count-1 }">
    <li><my:link target="" currentPageNumber="${ current + 1 }" limit="${ limit }" ariaLabel="Next">
        <span aria-hidden="true">&raquo;</span></my:link>
    </li>
</c:if>
