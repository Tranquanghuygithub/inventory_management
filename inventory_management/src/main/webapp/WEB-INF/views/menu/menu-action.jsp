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
                    	 servletRelativeAction="/menu/update-permission" method="POST" accept-charset="ISO-8859-1">
                      <div class="item form-group">
                        <label class="col-form-label col-md-3 col-sm-3 label-align" for="code">Role <span class="required text-danger">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 ">
                          <form:select path="roleId" cssClass="form-control">
                          	<form:options items="${mapRoles }"/>
                          </form:select>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label class="col-form-label col-md-3 col-sm-3 label-align" for="name">Menu <span class="required text-danger">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 ">
                          <form:select path="menuId" cssClass="form-control">
                          	<form:options items="${mapMenus }"/>
                          </form:select>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label for="description" class="col-form-label col-md-3 col-sm-3 label-align">Permission</label>
                        <div class="col-md-6 col-sm-6 ">
                          <form:radiobutton path="permission" value="1"/>Yes
                          <form:radiobutton path="permission" value="0"/>No
                        </div>
                      </div>
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
				$('#menulistId').addClass('current-page').siblings()
						.removeClass('current-page');
				var parent = $('#menulistId').parents('li');
				parent.addClass('active').siblings().removeClass('active');
				$('#menulistId').parents().show();
	});
	function cancel(){
		window.location.href="<c:url value = "/menu/list"/>";
	}
</script>