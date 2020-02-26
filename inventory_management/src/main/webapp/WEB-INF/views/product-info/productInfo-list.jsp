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
                    	servletRelativeAction="/product-info/list/1" method="POST" accept-charset="ISO-8859-1">
                      
                      <div class="item form-group">
                        <label class="col-form-label col-md-3 col-sm-3 label-align" for="id">ID</label>
                        <div class="col-md-6 col-sm-6 ">
                          <form:input path="id" cssClass="form-control "></form:input>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label class="col-form-label col-md-3 col-sm-3 label-align" for="code">Code</label>
                        <div class="col-md-6 col-sm-6 ">
                          <form:input path="code" cssClass="form-control"></form:input>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label for="description" class="col-form-label col-md-3 col-sm-3 label-align">Name</label>
                        <div class="col-md-6 col-sm-6 ">
                          <form:input path="name" cssClass="form-control"></form:input>
                        </div>
                      </div>
                      
                      <div class="ln_solid"></div>
                      <div class="item form-group">
                      	<div class="col-md-3 col-sm-3"></div>
                        <div  class="col-md-6 col-sm-6">
	                          <button type="submit" class="btn btn-success">Submit</button>
                        </div>
                      </div>
                    
                    </form:form>
                  </div>
					<div class="container">
						<a class="btn btn-app" href="<c:url value = "/product-info/add"/>"><i class="fa fa-plus"></i>Add ProductInfo</a>
					</div>
                  <div class="x_content">

                    <div class="table-responsive">
                      <table class="table table-striped jambo_table bulk_action">
                        <thead>
                          <tr class="headings">
                            <th class="column-title">#</th>
                            <th class="column-title">Id</th>
                            <th class="column-title">Name</th>
                            <th class="column-title">Code</th>
                            <th class="column-title">Category</th>
                            <th class="column-title">Image Product</th>
                            <th class="column-title no-link last text-center" colspan="3">
                            	<span class="nobr">Action</span>
                            </th>
                          </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${productInfos }" var="productInfo" varStatus="loop">
	                        <c:choose>
	                        	<c:when test="${loop.index%2==0 }">
		                          <tr class="even pointer">	                        	
	                        	</c:when>
	                        	<c:otherwise>
	                        		 <tr class="odd pointer">	
	                        	</c:otherwise>
	                        </c:choose>
	                            <td class=" ">${pageInfo.getOffset()+loop.index+1 }</td>
	                            <td class=" ">${productInfo.id }</td>
	                            <td class=" ">${productInfo.name }</td>
	                            <td class=" ">${productInfo.code }</td>
	                            <td class="a-right a-right">${productInfo.category.name }</td>
	                            <td class="a-right a-right">
	                            	<img alt="" src="<c:url value = "${productInfo.imgUrl }"/>" width="100px" height="100px">
	                            </td>
	                            <td class="text-center last">
	                            	<a href="<c:url value="/product-info/view/${productInfo.id }"/>" class="btn btn-info btn-sm">View</a>
	                            </td>
	                            <td class="text-center last">
	                            	<a href="<c:url value="/product-info/edit/${productInfo.id }"/>" class="btn btn-warning btn-sm">Edit</a>
	                            </td>
	                            <td class="text-center last">
	                            	<a href="javascript:void(0);" onclick="confirmDeleteCategory(${productInfo.id});" class="btn btn-danger btn-sm">Delete</a>
	                            </td>
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
	function confirmDeleteCategory(id) {
		if(confirm('Do you want delete this record?')){
			 window.location.href = '<c:url value="/product-info/delete/"/>'+id;
		 }
	}
	function gotoPage(page){
		 $('#searchForm').attr('action','<c:url value="/product-info/list/"/>'+page);
		 $('#searchForm').submit();
	 }
	$(document).ready(function(){
		processMessage();
	});
	function processMessage() {
		var msgSuccess = '${msgSuccess}';
		var msgError = '${msgError}';
		if (msgSuccess) {
			new PNotify({
                title: 'Success',
                text: msgSuccess,
                type: 'success',
                styling: 'bootstrap3'
            });
		}
		if (msgError) {
			new PNotify({
                title: 'Oh No!',
                text: msgError,
                type: 'error',
                styling: 'bootstrap3'
            });
		}
	}
</script>
