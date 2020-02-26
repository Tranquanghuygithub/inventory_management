<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="right_col" role="main">
	<div class="">
		<div class="row" style="display: block;">
			<div class="col-md-12 col-sm-12  ">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>${titlePage }</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  	 <div class="x_content">
                    <br />
                    <form:form modelAttribute="searchForm"  cssClass="form-horizontal form-label-left" 
                    	servletRelativeAction="/history/1" method="POST" accept-charset="ISO-8859-1">
                      
                      <div class="item form-group">
                        <label class="col-form-label col-md-3 col-sm-3 label-align" for="id">Category</label>
                        <div class="col-md-6 col-sm-6 ">
                          <form:input path="productInfo.category.name" cssClass="form-control "></form:input>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label class="col-form-label col-md-3 col-sm-3 label-align" for="code">Name</label>
                        <div class="col-md-6 col-sm-6 ">
                          <form:input path="productInfo.name" cssClass="form-control" ></form:input>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label for="description" class="col-form-label col-md-3 col-sm-3 label-align">Code</label>
                        <div class="col-md-6 col-sm-6 ">
                          <form:input path="productInfo.code" cssClass="form-control"></form:input>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label for="description" class="col-form-label col-md-3 col-sm-3 label-align">Action Name</label>
                        <div class="col-md-6 col-sm-6 ">
                          <form:input path="actionName" cssClass="form-control"></form:input>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label for="description" class="col-form-label col-md-3 col-sm-3 label-align">Code</label>
                        <div class="col-md-6 col-sm-6 ">
                          <form:select path="type" cssClass="form-control">
                          	<form:options items="${mapType }"/>
                          </form:select>
                        </div>
                      </div>
                      
                      <div class="ln_solid"></div>
                      <div class="item form-group">
                      	<div class="col-md-3 col-sm-3"></div>
                        <div  class="col-md-6 col-sm-6">
	                          <button type="submit" class="btn btn-success">Search</button>
                        </div>
                      </div>
                    
                    </form:form>
                  </div>
					
                  <div class="x_content">

                    <div class="table-responsive">
                      <table class="table table-striped jambo_table bulk_action">
                        <thead>
                          <tr class="headings">
                            <th class="column-title">#</th>
                            <th class="column-title">Category</th>
                            <th class="column-title">Code</th>
                            <th class="column-title">Name</th>
                            <th class="column-title">Quantity</th>
                            <th class="column-title">Price</th>
                            <th class="column-title">Type</th>
                            <th class="column-title">Action Name</th>
                          </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${histories }" var="history" varStatus="loop">
	                        <c:choose>
	                        	<c:when test="${loop.index%2==0 }">
		                          <tr class="even pointer">	                        	
	                        	</c:when>
	                        	<c:otherwise>
	                        		 <tr class="odd pointer">	
	                        	</c:otherwise>
	                        </c:choose>
	                            <td class=" ">${pageInfo.getOffset()+loop.index+1 }</td>
	                            <td class=" ">${history.productInfo.category.name }</td>
	                            <td class=" ">${history.productInfo.code }</td>
	                            <td class=" ">${history.productInfo.name }</td>
	                            <td class=" ">${history.qty }</td>
	                            <td class="price" style="font-size : 20px">${history.price }</td>
	                           	<c:choose>
	                           		<c:when test="${history.type == 1 }">
	                           			<td class="a-right a-right ">Goods Receipt</td>
	                           		</c:when>
	                           		<c:otherwise>
	                           			<td class="a-right">Goods Issues</td>
	                           		</c:otherwise>
	                           	</c:choose>
	                            <td class=" ">${history.actionName }</td>
	                          </tr>
	                          
	                        </c:forEach>
                        </tbody>
                      </table>
						<jsp:include page="../layout/paging.jsp"></jsp:include>
                    </div>
                  </div>
                </div>
              </div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function gotoPage(page){
		 $('#searchForm').attr('action','<c:url value="/history/"/>'+page);
		 $('#searchForm').submit();
	 }
	$(document).ready(function () {
		$('.price').each(function(){
			 $(this).text(numeral($(this).text()).format('0,0'));
		 }) 
	});
</script>
