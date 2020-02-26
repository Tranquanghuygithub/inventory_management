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
                    <h2>Menu List</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  
                  <div class="x_content">
	                    <br />
	                    <form:form modelAttribute="searchForm"  cssClass="form-horizontal form-label-left" 
	                    	servletRelativeAction="/menu/list/1" method="POST" accept-charset="ISO-8859-1">
	                      
	                      
	                    </form:form>
	                    <a href="<c:url value="/menu/permission"/>" class="btn btn-app"><i
							class="fa fa-plus"></i>Permission</a>
                  </div>
                  <div class="x_content">

                    <div class="table-responsive">
                      <table class="table table-striped jambo_table bulk_action">
                        <thead>
                          <tr class="headings">
                            <th class="column-title" rowspan="2" style="border-left: 2px solid;">#</th>
                            <th class="column-title" rowspan="2" style="border-left: 2px solid;">URL</th>
                            <th class="column-title text-center" rowspan="2" style="border-left: 2px solid;">Status</th>
                            <th class="column-title no-link last text-center" colspan="${roles.size() }" style="border-left: 2px solid;">Role</th>
                          </tr>
                          <tr>
                          	<c:forEach var="role" items="${roles }">
                          		<th class="colum-title text-center">${role.roleName }</th>
                          	</c:forEach>
                          </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${menuList }" var="menu" varStatus="loop">
	                        <c:choose>
	                        	<c:when test="${loop.index%2==0 }">
		                          <tr class="even pointer">	                        	
	                        	</c:when>
	                        	<c:otherwise>
	                        		 <tr class="odd pointer">	
	                        	</c:otherwise>
	                        </c:choose>
	                            <td class=" ">${pageInfo.getOffset()+loop.index+1 }</td>
	                            <td class="">${menu.url}</td>
	                            <c:choose>
	                            	<c:when test="${menu.activeFlag==1 }">
		                            	<td class="text-center"">
	                            			<a href="javascript:void(0);" onclick="confirmChange(${menu.id},${menu.activeFlag })"
	                            				 class="btn btn-primary">Enable</a>
	                            		</td>
	                            	</c:when>
		                            <c:otherwise>
	                            		<td class="text-center"">
	                            			<a href="javascript:void(0);" onclick="confirmChange(${menu.id},${menu.activeFlag })"
	                            				 class="btn btn-danger">Disable</a>
	                            		</td>
		                            </c:otherwise>
	                            </c:choose>
	                            <c:forEach items="${menu.mapAuth }" var="auth">
	                            	<c:choose>
	                            	
	                            		<c:when test="${auth.value==1 }">
	                            			<td class="text-center" style="color: green;"><i class="fa fa-check"></i></td>
	                            		</c:when>
	                            		
	                            		<c:otherwise>
	                            			<td class="text-center" style="color: red;"><i class="fa fa-times"></i></td>
	                            		</c:otherwise>
	                            		
	                            	</c:choose>
	                            </c:forEach>
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
	function confirmChange(id, flag) {
		var msg = flag == 1 ? 'Do you want disable this menu ?' : 'Do you want enable this menu ?';
		if (confirm(msg)) {
			window.location.href = '<c:url value="/menu/change-status/"/>' + id;
		}
	}
	function gotoPage(page){
		 $('#searchForm').attr('action','<c:url value="/menu/list/"/>'+page);
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
