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
                    <h2>Role List</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  	 <div class="x_content">
                    <br />
                    <form:form modelAttribute="searchForm"  cssClass="form-horizontal form-label-left" 
                    	servletRelativeAction="/role/list/1" method="POST" accept-charset="ISO-8859-1">
                     
                    
                    </form:form>
                  </div>
					<div class="container">
						<a class="btn btn-app" href="<c:url value = "/role/add"/>"><i class="fa fa-plus"></i>Add Category</a>
					</div>
                  <div class="x_content">

                    <div class="table-responsive">
                      <table class="table table-striped jambo_table bulk_action">
                        <thead>
                          <tr class="headings">
                            <th class="column-title">#</th>
                            <th class="column-title">Id</th>
                            <th class="column-title">Name</th>
                            <th class="column-title">Description</th>
                            <th class="column-title no-link last text-center" colspan="3">
                            	<span class="nobr">Action</span>
                            </th>
                          </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${roles }" var="role" varStatus="loop">
	                        <c:choose>
	                        	<c:when test="${loop.index%2==0 }">
		                          <tr class="even pointer">	                        	
	                        	</c:when>
	                        	<c:otherwise>
	                        		 <tr class="odd pointer">	
	                        	</c:otherwise>
	                        </c:choose>
	                            <td class=" ">${pageInfo.getOffset()+loop.index+1 }</td>
	                            <td class=" ">${role.id }</td>
	                            <td class=" ">${role.roleName }</td>
	                            <td class="a-right a-right ">${role.description }</td>
	                            <td class="text-center last"><a href="<c:url value="/role/view/${role.id }"/>" class="btn btn-info btn-sm">View</a></td>
	                            <td class="text-center last"><a href="<c:url value="/role/edit/${role.id }"/>" class="btn btn-warning btn-sm">Edit</a></td>
	                            <td class="text-center last"><a href="javascript:void(0);" onclick="confirmDeleteRole(${role.id});" class="btn btn-danger btn-sm">Delete</a></td>
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
	function confirmDeleteRole(id) {
		if(confirm('Do you want delete this record?')){
			 window.location.href = '<c:url value="/role/delete/"/>'+id;
		 }
	}
	function gotoPage(page){
		 $('#searchForm').attr('action','<c:url value="/role/list/"/>'+page);
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
