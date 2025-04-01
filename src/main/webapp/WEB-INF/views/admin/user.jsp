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
<title>Tài khoản</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="/WEB-INF/views/admin/layouts/css.jsp"/>
	<style>
		.toast-success{
			border-radius: 10px !important;
			padding: 15px !important;
			box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2) !important;
			font-weight: 500 !important;
			position: fixed !important;
			top: 20px !important;
			right: 20px !important;
			margin: 0 !important;
			z-index: 9999 !important;
		}
	</style>
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
			<sf:form modelAttribute="searchModel" class="list" action="${base }/admin/user" method="get">
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
					style="margin: 0 30px; padding-bottom: 15px">
					<div class="table-responsive">
						<table class="table text-center">
							<thead class="text-uppercase bg-primary">
								<tr class="text-white">
									<th scope="col">ID</th>
									<th scope="col">Tên đăng nhập</th>									
									<th scope="col">Email</th>
									<th scope="col">Địa chỉ</th>
									<th scope="col">Ngày Tạo</th>
									<th scope="col">Role</th>
									<th scope="col">Status</th>
									<th scope="col">Chức năng</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${userPage.getContent()}" var="user"
									varStatus="loop">
									<tr>
										<th scope="row">${loop.index + 1}</th>
										<td>${user.username }</td>
										<td>${user.email }</td>
										<td>${user.address }</td>
										<td>${user.createdDate }</td>

										<td>
											<div class="dropdown">
												<button class="btn btn-secondary dropdown-toggle" type="button"
														id="dropdownMenuButton" data-toggle="dropdown"
														aria-haspopup="true" aria-expanded="false"
														style="background-color: transparent; border-color: transparent;">
													<c:choose>
														<c:when test="${user.isAdmin()}">
                                                    <span class="badge badge-success"
														  style="display: inline-block; width: 100%; height: 100%; font-size: 15px;">ADMIN</span>
														</c:when>
														<c:when test="${!user.isAdmin()}">
                                                    	<span class="badge badge-warning"
														  style="display: inline-block; width: 100%; height: 100%;  font-size: 15px;">USER</span>
														</c:when>
													</c:choose>
												</button>
												<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
													<a class="dropdown-item" href="#" onclick="updateRole(${user.username}, 4)">USER</a>
													<a class="dropdown-item" href="#" onclick="updateRole(${user.username}, 3)">ADMIN</a>
												</div>
											</div>
										</td>

										<td>
											<button class="btn btn-secondary dropdown-toggle" type="button"
													aria-haspopup="true" aria-expanded="false"
													style="background-color: transparent; border-color: transparent;">
												<c:choose>
													<c:when test="${user.status == false}">
                                                    <span class="badge badge-danger"
														  style="display: inline-block; width: 100%; height: 100%; font-size: 15px;">Deactivate</span>
													</c:when>
													<c:when test="${user.status == true}">
                                                    <span class="badge badge-success"
														  style="display: inline-block; width: 100%; height: 100%;  font-size: 15px;">Active</span>
													</c:when>
												</c:choose>
											</button>
										</td>
										<td>
											<c:choose>
												<c:when test="${user.status == false or user.isAdmin()}">
													<button class="btn btn-danger" disabled>Xóa</button>
												</c:when>
												<c:otherwise>
													<a class="deactivate-role" data-userName="${user.username}"
													   href="${base }/admin/deleteUser/${user.id }">
														<button class="btn btn-danger" role="button">Xóa</button>

													</a>
												</c:otherwise>
											</c:choose>

										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					</div>
				</div>
			<c:if test="${userPage.totalPages > 1}"> <%-- Chỉ hiển thị phân trang nếu có nhiều hơn 1 trang --%>
				<div class="float-end align-items-center mt-2">
					<!-- Nút Trang trước -->
					<button class="btn btn-outline-primary" onclick="navigateToPage(${userPage.number - 1})" ${userPage.first ? 'disabled' : ''}>
						<< Trang trước
					</button>

					<!-- Hiển thị thông tin trang hiện tại -->
					<span class="p-2">${userPage.number + 1} / ${userPage.totalPages}</span>

					<!-- Nút Trang sau -->
					<button class="btn btn-outline-primary" onclick="navigateToPage(${userPage.number + 1})" ${userPage.last ? 'disabled' : ''}>
						Trang sau >>
					</button>
				</div>
			</c:if>
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

		<div class="modal fade" id="deleteUserModal" tabindex="-1" role="dialog" aria-labelledby="deleteUserModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="deleteUserModalLabel">Xác nhận loại bỏ User</h5>
						<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">×</span>
						</button>
					</div>
					<div class="modal-body">
						Bạn có chắc chắn muốn hủy <strong id="userDisplay"></strong> không?
						<p class="text-danger">Hành động này không thể hoàn tác!</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy bỏ</button>
						<a href="#" id="confirmDeleteUserLink" class="btn btn-danger">Xác nhận</a>
					</div>
				</div>
			</div>
		</div>

	</div>
	<!-- page container area end -->
	<!-- offset area start -->
	<jsp:include page="/WEB-INF/views/admin/layouts/offset.jsp"/>
	<jsp:include page="/WEB-INF/views/admin/layouts/js.jsp"/>
</body>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Toastify JS -->
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('.deactivate-role').click(function(e) {
			e.preventDefault();

			var rejectLink = $(this);
			var orderId = rejectLink.data('data-userName');

			// Set the orderId in the modal body
			$('#userDisplay').text(orderId);

			// Update the href of the "Confirm Reject" button in the modal
			$('#confirmDeleteUserLink').attr('href', rejectLink.attr('href'));

			// Show the modal
			$('#deleteUserModal').modal('show');
		})
	});

	document.addEventListener('DOMContentLoaded', function() {
		<c:if test="${not empty successMessage}">
		Toastify({
			text: "${successMessage}",
			duration: 3000,
			gravity: "top",
			position: "right",
			backgroundColor: "linear-gradient(135deg, #00b09b 0%, #268112 100%)",
			className: "toast-success",
			stopOnFocus: true,
			offset: {
				x: 20,  // Khoảng cách từ lề phải
				y: 20   // Khoảng cách từ đỉnh màn hình
			}
		}).showToast();
		</c:if>
	});

	document.addEventListener('DOMContentLoaded', function() {
		<c:if test="${not empty errorMessage}">
		Toastify({
			text: "${errorMessage}",
			duration: 3000,
			gravity: "top",
			position: "right",
			backgroundColor: "linear-gradient(135deg, #FF5252 0%, #B71C1C 100%)",
			className: "toast-error",
			stopOnFocus: true,
			offset: {
				x: 20,  // Khoảng cách từ lề phải
				y: 20   // Khoảng cách từ đỉnh màn hình
			}
		}).showToast();
		</c:if>
	});

	function updateRole(userName, role) {
		let data = {
			userId: userName,
			role: role
		};

		$.ajax({
			url: '/admin/user/updateRole',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(data),
			success: function (response) {
				// Xử lý khi cập nhật thành công (nếu cần)
				location.reload(); // Cập nhật trang sau khi cập nhật trạng thái
			},
			error: function (xhr, status, error) {
				// Xử lý khi có lỗi (nếu cần)
				console.error(xhr.responseText);
			}
		});
	}

	function navigateToPage(pageNumber) {
		document.getElementById('pageInput').value = pageNumber;
		document.getElementById('filterForm').submit();
	}

</script>

</html>
