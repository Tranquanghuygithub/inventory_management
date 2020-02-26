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
                    <h2>Goods Issue</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  	 <div class="x_content">
                    <br />
                    <form:form modelAttribute="searchForm"  cssClass="form-horizontal form-label-left" 
                    	servletRelativeAction="/goods-issue/list/1" method="POST" accept-charset="ISO-8859-1">
                      
                      <div class="item form-group">
                        <label class="col-form-label col-md-3 col-sm-3 label-align" for="code">Code</label>
                        <div class="col-md-6 col-sm-6 ">
                          <form:input path="code" cssClass="form-control"/>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label class="col-form-label col-md-3 col-sm-3 label-align" for="fromDate">From Date</label>
                        <div class="col-md-6 col-sm-6 ">
                        	<div class="input-group date" id="fromDatePicker">
	                          <form:input path="fromDate" class="form-control "/>
	                          <span class="input-group-addon">
	                          	<span class="glyphicon glyphicon-list"></span>
	                          </span>
                        	</div>
                        </div>
                      </div>
                      
                      <div class="item form-group">
                        <label class="col-form-label col-md-3 col-sm-3 label-align" for="toDate">To Date</label>
                        <div class="col-md-6 col-sm-6 ">
                        	<div class="input-group date" id="toDatePicker">
	                          <form:input path="toDate" class="form-control "/>
	                          <span class="input-group-addon">
	                          	<span class="glyphicon glyphicon-list"></span>
	                          </span>
                        	</div>
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
						<a class="btn btn-app" href="<c:url value = "/goods-issue/add"/>"><i class="fa fa-plus"></i>Add Invoice</a>
						<a href="<c:url value ="/goods-issue/export"/>"><i class="fa fa-cloud-download"></i> Export to Excel</a>
					</div>
                  <div class="x_content">

                    <div class="table-responsive">
                      <table class="table table-striped jambo_table bulk_action">
                        <thead>
                          <tr class="headings">
                            <th class="column-title">#</th>
                            <th class="column-title">Code</th>
                            <th class="column-title">Quantity</th>
                            <th class="column-title">Price</th>
                            <th class="column-title">Product Name</th>
                            <th class="column-title">Update Date</th>
                            <th class="column-title no-link last text-center" colspan="3">
                            	<span class="nobr">Action</span>
                            </th>
                          </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${invoices }" var="invoice" varStatus="loop">
	                        <c:choose>
	                        	<c:when test="${loop.index%2==0 }">
		                          <tr class="even pointer">	                        	
	                        	</c:when>
	                        	<c:otherwise>
	                        		 <tr class="odd pointer">	
	                        	</c:otherwise>
	                        </c:choose>
	                            <td class=" ">${pageInfo.getOffset()+loop.index+1 }</td>
	                            <td class=" ">${invoice.code }</td>
	                            <td class=" ">${invoice.qty }</td>
	                            <td class="price" style="font-size : 20px ">${invoice.price }</td>
	                            <td class="a-right a-right ">${invoice.productInfo.name }</td>
	                            <td class="a-right a-right ">${invoice.updateDate }</td>
	                            
	                            <td class="text-center last"><a href="<c:url value="/goods-issue/view/${invoice.id }"/>" class="btn btn-info btn-sm">View</a></td>
	                            <td class="text-center last"><a href="<c:url value="/goods-issue/edit/${invoice.id }"/>" class="btn btn-warning btn-sm">Edit</a></td>
	                            <td class="text-center last"><a href="javascript:void(0);" onclick="confirmDeleteCategory(${invoice.id});" class="btn btn-danger btn-sm">Delete</a></td>
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
			 window.location.href = '<c:url value="/goods-issue/delete/"/>'+id;
		 }
	}
	function gotoPage(page){
		 $('#searchForm').attr('action','<c:url value="/goods-issue/list/"/>'+page);
		 $('#searchForm').submit();
	 }
	 $(document).ready(function(){
		 processMessage();
		 $('#fromDatePicker').datetimepicker({
			 format : 'YYYY-MM-DD HH:mm:ss'
		 });
		 $('#toDatePicker').datetimepicker({
			 format : 'YYYY-MM-DD HH:mm:ss'
		 });
		  $('.price').each(function(){
			 $(this).text(numeral($(this).text()).format('0,0'));
		 }) 
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
