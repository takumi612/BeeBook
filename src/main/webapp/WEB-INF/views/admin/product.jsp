<%@ page contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<!-- SPRING FORM -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html class="no-js" lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Product</title>
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
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> to improve your experience.</p>
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
        <jsp:include page="/WEB-INF/views/admin/layouts/title.jsp"/>
        <!-- page title area end -->
            <div class="main-content-inner">
                <!-- sales report area start -->

                <sf:form modelAttribute="searchModel" class="list" id="filterForm" action="${base }/admin/product" method="post">
                    <div class="card-body"
                         style="display: flex; justify-content: space-between">
                        <div style="display: flex; padding-right: 15px">

                            <sf:input type="hidden" path="pageNumber" name="pageNumber" id="pageInput" class="form-control" value="0"/>

                            <div class="search-container " style="margin-right: 20px; width: 170px;">
                                <label for="keyword">Tìm kiếm</label>
                                <sf:input path="keyword" type="text" id="keyword" name="keyword" class="form-control"
                                       placeholder="Search" value="${searchModel.keyword}"/>
                            </div>

                            <div style="margin-right: 20px; width: 170px;">
                            <label for="categoryId" class="form-label">Danh mục</label>
                            <sf:select path="categoryId" class="form-control" name="categoryId" id="categoryId">
                                    <option value="0">All</option>
                                    <c:forEach items="${categories}" var="category">
                                        <sf:option value="${category.id }">${category.name }</sf:option>
                                    </c:forEach>
                                </sf:select>
                            </div>

                            <div class="search-container" style="margin-right: 20px; width: 170px;">
                                <label for="statusFilter" class="form-label">Trạng thái Sản phẩm</label>
                                <sf:select path="productStatus" class="form-control" id="statusFilter" name="status">
                                    <sf:option value="">All</sf:option>
                                    <sf:option value="1">Available</sf:option>
                                    <sf:option value="0">Unavailable</sf:option>
                                </sf:select>
                            </div>

                            <span style="margin-right: 5px; width: 10px;" ></span>

                            <div style="margin-right: 20px; width: 170px;">
                                <label for="sortByFilter" class="form-label">Sort By</label>
                                <sf:select path="sortBy"  class="form-control" id="sortByFilter" name="sortBy">
                                    <sf:option value="quantity">Theo Số lượng</sf:option>
                                    <sf:option value="price">Theo Giá</sf:option>
                                </sf:select>
                            </div>

                            <div style="margin-right: 20px; width: 170px;">
                                <label for="sortDirectionFilter" class="form-label">Direction</label>
                                <sf:select path="sortDirection" class="form-control" id="sortDirectionFilter" name="sortDirection">
                                    <sf:option value="desc">Giảm dần</sf:option>
                                    <sf:option value="asc">Tăng dần</sf:option>
                                </sf:select>
                            </div>

                            <button type="submit" id="btnSearch" name="btnSearch"
                                    value="Search" class="btn btn-flat btn-outline-secondary">Search
                            </button>
                        </div>
                        <div>
                                <a href="${base }/admin/addProduct">
                                    <button type="button"
                                            class="btn btn-flat btn-outline-secondary mb-3">Thêm mới sản phẩm</button>
                                </a>
                        </div>
                    </div>
                </sf:form>

                <a href="${base }/admin/product" style="margin: 10px">
                        <button class="btn btn-outline-secondary" style="margin: 10px;">Clear</button>
                    </a>
            </div>

            <!-- Dark table start -->
            <!-- Dark table end -->

            <div class="single-table"
                 style="margin: 0 30px; padding-bottom: 15px">
                <div class="table-responsive">
                    <table class="table text-center">
                        <thead class="text-uppercase bg-primary">
                        <tr class="text-white">
                            <th scope="col">STT</th>
                            <th scope="col">Tiêu đề</th>
                            <th scope="col">Giá</th>
                            <th scope="col">Dang mục</th>
                            <th scope="col">Nhà Xuất Bản</th>
                            <th scope="col">Số lượng</th>
                            <th scope="col">Trạng thái</th>
                            <th scope="col">Avatar</th>
                            <th scope="col">Chức năng</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${productPage.getContent()}" var="product" varStatus="loop">
                                <tr>
                                    <th scope="row">${loop.index + 1}</th>
                                    <td>${product.title }</td>
                                    <td><fmt:setLocale value="vi_VN" scope="session"/> <fmt:formatNumber
                                            value="${product.price }" type="currency"/></td>
                                    <td>${product.categoriesName }</td>
                                    <td>${product.manufacturerName }</td>
                                    <td>${product.quantity }</td>
                                    <td>
                                        <c:choose>
                                        <c:when test="${product.status == false}">
                                                    <span class="badge badge-danger"
                                                          style="display: inline-block; width: 100%; height: 100%; font-size: 15px;">Unavailable</span>
                                        </c:when>
                                        <c:when test="${product.status == true}">
                                                    <span
                                                          style="display: inline-block; width: 100%; height: 100%;  font-size: 15px;">Available</span>
                                        </c:when>
                                        </c:choose>
                                    </td>

                                    <td><img src="${base }/upload/${product.avatar}"
                                             width="100" height="100"></td>
                                    <td><a class="btn btn-primary"
                                           href="${base }/admin/updateProduct/${product.id}" role="button">Sửa</a>

                                        <a class="btn btn-danger delete-product-link" role="button" data-product-name="${product.title}"
                                           href="${base }/admin/deleteProduct/${product.id}">Xóa</a>
                                    </td>
                                </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>

        <c:if test="${productPage.totalPages > 1}"> <%-- Chỉ hiển thị phân trang nếu có nhiều hơn 1 trang --%>
            <div class="float-end align-items-center mt-2">
                <!-- Nút Trang trước -->
                <button class="btn btn-outline-primary" onclick="navigateToPage(${productPage.number - 1})" ${productPage.first ? 'disabled' : ''}>
                    << Trang trước
                </button>

                <!-- Hiển thị thông tin trang hiện tại -->
                <span class="p-2">${productPage.number + 1} / ${productPage.totalPages}</span>

                <!-- Nút Trang sau -->
                <button class="btn btn-outline-primary" onclick="navigateToPage(${productPage.number + 1})" ${productPage.last ? 'disabled' : ''}>
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
    

    <div class="modal fade" id="deleteProductModal" tabindex="-1" role="dialog" aria-labelledby="deleteProductModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteProductModalLabel">Xác nhận loại bỏ mặt hàng</h5>
                    <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    Bạn có chắc chắn muốn xóa mặt hàng <strong id="productDisplay"></strong> không?
                    <p class="text-danger">Hành động này không thể hoàn tác!</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy bỏ</button>
                    <a href="#" id="confirmDeleteProductLink" class="btn btn-danger">Xác nhận</a>
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
        $('.delete-product-link').click(function(e) {
            e.preventDefault();

            var rejectLink = $(this);
            var orderId = rejectLink.data('data-product-name');

            // Set the orderId in the modal body
            $('#productDisplay').text(orderId);

            // Update the href of the "Confirm Reject" button in the modal
            $('#confirmDeleteProductLink').attr('href', rejectLink.attr('href'));

            // Show the modal
            $('#deleteProductModal').modal('show');
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
            backgroundColor: "linear-gradient(135deg, #00b09b 0%, #81170d 100%) ",
            className: "toast-error",
            stopOnFocus: true,
            offset: {
                x: 20,  // Khoảng cách từ lề phải
                y: 20   // Khoảng cách từ đỉnh màn hình
            }
        }).showToast();
        </c:if>
    });


    function navigateToPage(pageNumber) {
        document.getElementById('pageInput').value = pageNumber;
        document.getElementById('filterForm').submit();
    }
</script>

</html>
