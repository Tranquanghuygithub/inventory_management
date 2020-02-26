<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="right_col" role="main">
	<div>
		<div class="page-title">
			<div class="title_left">
				<h2>${titlePage }</h2>
			</div>
		</div>
            <div class="clearfix"></div>
            <div class="row">
              <div class="col-md-12 col-sm-12 ">
                <div class="x_panel">
                  <div class="x_content">
                    <br />
                    <form:form id="demo-form2" modelAttribute="modelForm"  cssClass="form-horizontal form-label-left"
                    	 servletRelativeAction="/user/save" method="POST" accept-charset="ISO-8859-1">
						<form:hidden path="id"/>
                      <div class="item form-group">
                        <label class="col-form-label col-md-3 col-sm-3 label-align" for="code">Full Name<span class="required text-danger">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 ">
                          <form:input path="name" cssClass="form-control " disabled="${viewOnly }"></form:input>
                          <div class="has-error">
								<form:errors path="name" cssClass="help-block"></form:errors>
							</div>
                        </div>
                      </div>
                      <div class="item form-group">
                        <label class="col-form-label col-md-3 col-sm-3 label-align" for="name">Email <span class="required text-danger">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 ">
                          <form:input path="email" cssClass="form-control" disabled="${viewOnly }"/>
                          <div class="has-error">
								<form:errors path="email" cssClass="help-block"></form:errors>
							</div>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label for="description" class="col-form-label col-md-3 col-sm-3 label-align">Role</label>
                        <div class="col-md-6 col-sm-6 ">
                        	<c:choose>
                        		<c:when test="${!viewOnly }">
	                   				<form:select path="roleID" cssClass = "form-control" disabled="${viewOnly }">
	                   					<form:options items="${mapRole }"/>
	                   				</form:select>
	                   				<div class="has-error">
	                   					<form:errors path="roleID" cssClass="help-block"></form:errors>
	                   				</div>
                        		</c:when>
                        		<c:otherwise>
                        			<form:input path="roleName" disabled="true" cssClass="form-control col-md-7 col-xs-12"/>
                        		</c:otherwise>
                        	</c:choose>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label for="description" class="col-form-label col-md-3 col-sm-3 label-align">UserName</label>
                        <div class="col-md-6 col-sm-6 ">
                   				<form:input path="userName" cssClass="form-control" disabled="${viewOnly }"/>
	                          <div class="has-error">
									<form:errors path="userName" cssClass="help-block"></form:errors>
								</div>
                        </div>
                      </div>
                      
                      <c:if test="${editModel == null }">
	                      <div class="item form-group">
	                        <label for="description" class="col-form-label col-md-3 col-sm-3 label-align">Password
	                        	<span class="required">*</span>
	                        </label>
	                        <div class="col-md-6 col-sm-6 ">
	                   				<form:input path="password" cssClass="form-control" type = "password"
	                   					 disabled="${viewOnly }"/>
		                          <div class="has-error">
										<form:errors path="password" cssClass="help-block"></form:errors>
									</div>
	                        </div>
	                      </div>                      
                      </c:if>
                      
                      <div class="ln_solid"></div>
                      
                      <div class="item form-group">
                      	<div class="col-md-3 col-sm-3"></div>
                        <div  class="col-md-6 col-sm-6">
                          <button class="btn btn-primary" type="button" onclick="cancel();">Cancel</button>
                          <c:if test="${!viewOnly }">
							  <button class="btn btn-primary" type="reset">Reset</button>
	                          <button type="submit" class="btn btn-success">Submit</button>
                          </c:if>
                        </div>
                      </div>

                    </form:form>
                  </div>
                </div>
              </div>
            </div>
	</div>
</div>
<script type="text/javascript">

	$(document).ready(
			function () {
				$('#userlistId').addClass('current-page').siblings()
						.removeClass('current-page');
				var parent = $('#userlistId').parents('li');
				parent.addClass('active').siblings().removeClass('active');
				$('#userlistId').parents().show();
	});
	function cancel(){
		window.location.href="<c:url value = "/user/list"/>";
	}
</script>