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
            <form:form modelAttribute="searchForm" cssClass="form-horizontal form-label-left"
              servletRelativeAction="/product-in-stock/list/1" method="POST" accept-charset="ISO-8859-1">

              <div class="item form-group">
                <label class="col-form-label col-md-3 col-sm-3 label-align" for="id">CODE</label>
                <div class="col-md-6 col-sm-6 ">
                  <form:input path="productInfo.code" cssClass="form-control "></form:input>
                </div>
              </div>

              <div class="item form-group">
                <label class="col-form-label col-md-3 col-sm-3 label-align" for="code">Category</label>
                <div class="col-md-6 col-sm-6 ">
                  <form:input path="productInfo.category.name" cssClass="form-control"></form:input>
                </div>
              </div>

              <div class="item form-group">
                <label for="description" class="col-form-label col-md-3 col-sm-3 label-align">Name</label>
                <div class="col-md-6 col-sm-6 ">
                  <form:input path="productInfo.name" cssClass="form-control"></form:input>
                </div>
              </div>

              <div class="ln_solid"></div>
              <div class="item form-group">
                <div class="col-md-3 col-sm-3"></div>
                <div class="col-md-6 col-sm-6">
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
                    <th class="column-title">Image Product</th>
                    <th class="column-title">Quantity</th>
                    <th class="column-title">Price</th>
                  </tr>
                </thead>

                <tbody>
                  <c:forEach items="${productInStocks }" var="productInStock" varStatus="loop">
                    <c:choose>
                      <c:when test="${loop.index%2==0 }">
                        <tr class="even pointer">
                      </c:when>
                      <c:otherwise>
                        <tr class="odd pointer">
                      </c:otherwise>
                    </c:choose>
                    <td class=" ">${pageInfo.getOffset()+loop.index+1 }</td>
                    <td class=" ">${productInStock.productInfo.category.name }</td>
                    <td class=" ">${productInStock.productInfo.code }</td>
                    <td class=" ">${productInStock.productInfo.name }</td>
                    <td class="a-right a-right">
                      <img alt="" src="<c:url value = "${productInStock.productInfo.imgUrl }" />" width="100px" height="100px">
                      
                    </td>
                    <td class="a-right a-right">${productInStock.qty }</td>
                    <td class="a-right a-right price" style="font-size : 20px">${productInStock.price }</td>
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
  function gotoPage(page) {
    $('#searchForm').attr('action', '<c:url value="/product-in-stock/list/"/>' + page);
    $('#searchForm').submit();
  }
  $(document).ready(function(){
		  $('.price').each(function(){
			 $(this).text(numeral($(this).text()).format('0,0'));
		 }) 
	 });
</script>