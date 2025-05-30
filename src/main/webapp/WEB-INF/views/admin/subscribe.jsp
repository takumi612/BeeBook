<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!-- SPRING FORM -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!doctype html>
<html class="no-js" lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>Subscribe</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="/WEB-INF/views/admin/layouts/css.jsp"/>

</head>

<body>
	<!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
	<!-- preloader area start -->
	<div id="preloader">
		<div class="loader"></div>
	</div>
	<!-- preloader area end -->
	<!-- page container area start -->
	<div class="page-container">
		<!-- sidebar menu area start -->
		<jsp:include page="/WEB-INF/views/admin/layouts/sidebar.jsp"/>
		<!-- sidebar menu area end -->
		<!-- main content area start -->
		<div class="main-content">
			<!-- header area start -->
			<jsp:include page="/WEB-INF/views/admin/layouts/header.jsp"/>
			<!-- header area end -->
			<!-- page title area start -->
			<<jsp:include page="/WEB-INF/views/admin/layouts/title.jsp"/>
			<!-- page title area end -->
			<sf:form modelAttribute="searchModel" class="list" action="${base }/admin/subscribe" method="get">
				<div class="main-content-inner">
					<!-- sales report area start -->

					<div class="card-body"
						style="display: flex; justify-content: space-between">
						<div style="display: flex; padding-right: 15px">
							<sf:input path="pageNumber" type="hidden" id="pageInput" name="pageNumber" class="form-control"/>
							<sf:input path="keyword" type="text" id="keyword" name="keyword"
								class="form-control" placeholder="Search"
								value="${searchModel.keyword }"
								style="margin-right: 5px; height: 46px;"/>

							<button type="submit" id="btnSearch" name="btnSearch"
								value="Search" class="btn btn-flat btn-outline-secondary mb-3">Search</button>
						</div>
					</div>
				</div>
			</sf:form>

			<!-- Dark table start -->
				<!-- Dark table end -->

				<div class="single-table"
					style="margin: 0px 30px; padding-bottom: 15px">
					<div class="table-responsive">
						<table class="table text-center">
							<thead class="text-uppercase bg-primary">
								<tr class="text-white">
									<th scope="col">STT</th>
									<th scope="col">Email</th>
									<th scope="col">Ngày đăng ký</th>
									<th scope="col">Chức năng</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${subscribePage.getContent()}" var="subscribe"
									varStatus="loop">
									<tr>
										<th scope="row">${loop.index + 1}</th>
										<td>${subscribe.email }</td>
										<td>${subscribe.createdDate }</td>
										<td>
											<a class="btn btn-danger" role="button"
											   href="${base }/admin/deleteSubscribe/${subscribe.id }">Xóa</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					</div>
				</div>
				<!-- Paging -->
				<div class="row">
					<c:if test="${subscribePage.totalPages > 1}"> <%-- Chỉ hiển thị phân trang nếu có nhiều hơn 1 trang --%>
						<div class="float-end align-items-center mt-2">
							<!-- Nút Trang trước -->
							<button class="btn btn-outline-primary" onclick="navigateToPage(${subscribePage.number - 1})" ${subscribePage.first ? 'disabled' : ''}>
								<< Trang trước
							</button>

							<!-- Hiển thị thông tin trang hiện tại -->
							<span class="p-2">${subscribePage.number + 1} / ${subscribePage.totalPages}</span>

							<!-- Nút Trang sau -->
							<button class="btn btn-outline-primary" onclick="navigateToPage(${subscribePage.number + 1})" ${subscribePage.last ? 'disabled' : ''}>
								Trang sau >>
							</button>
						</div>
					</c:if>
				</div>
				
		</div>

		<!-- main content area end -->
		<!-- footer area start-->
		<footer>
			<div class="footer-area">
				<p>
					© Copyright 2018. All right reserved. Template by <a
						href="https://colorlib.com/wp/">Colorlib</a>.
				</p>
			</div>
		</footer>
		<!-- footer area end-->
	</div>
	<!-- page container area end -->
	<!-- offset area start -->
	<jsp:include page="/WEB-INF/views/admin/layouts/offset.jsp"/>
	<jsp:include page="/WEB-INF/views/admin/layouts/js.jsp"/>
</body>
<script type="text/javascript">
	function navigateToPage(pageNumber) {
		document.getElementById('pageInput').value = pageNumber;
		document.getElementById('filterForm').submit();
	}
</script>

</html>
