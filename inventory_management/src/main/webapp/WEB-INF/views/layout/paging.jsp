<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${pageInfo.getTotalPages() > 0}">
	<div class="container" style="text-align: center;" >
		<nav aria-label="..." style="display: inline-block;">
		  <ul class="pagination">
		  	<c:choose>
		  		<c:when test="${pageInfo.indexPage == 1 }">  		
				    <li class="page-item disabled">
				      <span class="page-link">Previous</span>
				    </li>
		  		</c:when>
		  		<c:otherwise>
			  		<li class="page-item">
				      <a class="page-link" href="javascript:void(0);" onclick="gotoPage(${pageInfo.indexPage-1});">Previous</a>
				    </li>
		  		</c:otherwise>
		  	</c:choose>
			  <c:forEach begin="1" end="${pageInfo.totalPages }" varStatus="loop">
			    <c:choose>
			    	<c:when test="${pageInfo.indexPage == loop.index }">
				    <li class="page-item active">
				      <span class="page-link">
				        <a href="javascript:void(0);">${loop.index }</a>
				        <span class="sr-only">(current)</span>
				      </span>
				    </li>
			    	</c:when>
			    	<c:otherwise>
				    	<li class="page-item">
				    		<a class="page-link" href="javascript:void(0);" onclick="gotoPage(${loop.index});">${loop.index }</a>
				    	</li>
			    	</c:otherwise>
			    </c:choose>
			  </c:forEach>
			  <c:choose>
			  	<c:when test="${pageInfo.indexPage == pageInfo.getTotalPages()}">
				  	<li class="page-item disabled">
					      <span class="page-link">Next</span>
					    </li>
			  	</c:when>
			  	<c:otherwise>
			    <li class="page-item">
			      <a class="page-link" href="javascript:void(0);" onclick="gotoPage(${pageInfo.indexPage+1});">Next</a>
			    </li>
			  	</c:otherwise>
			  </c:choose>
		  </ul>
		</nav>
	</div>
</c:if>