<%@ page language="java" contentType="text/html; charset=utf-8"
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
    <title>Chi tiết đơn hàng</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="/WEB-INF/views/admin/layouts/css.jsp"/>

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
        <
        <jsp:include page="/WEB-INF/views/admin/layouts/title.jsp"/>
            <div class="card mb-4 shadow">
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-9">
                            <h4 class="mb-3">Mã đơn hàng: #${orderInfo.code}</h4>
                            <div class="row">
                                <div class="col-md-6">
                                    <h5><strong>Tên: </strong>${orderInfo.customerName}</h5>
                                </div>
                                <div class="col-md-6">
                                    <h5><strong>SĐT: </strong>${orderInfo.customerPhone}</h5>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <h5><strong>Địa chỉ: </strong>${orderInfo.customerEmail}</h5>
                                </div>
                                <div class="col-md-6">
                                    <h5><strong>Email: </strong>${orderInfo.customerEmail}</h5>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <h5><strong>Ngày tạo: </strong>
                                        <fmt:parseDate value="${orderInfo.createdDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDate" type="both" />
                                        <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" /></h5>
                                </div>
                                <div class="col-md-6">
                                    <h5><strong>Phương thức thanh toán:</strong>
                                        <c:if test="${not empty orderInfo.paymentType}">
                                            ${orderInfo.paymentDisplayType}
                                        </c:if>
                                    </h5>
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:if test="${orderInfo.reason != null && orderInfo.paymentDisplayStatus eq 'Đã Hủy'}">
                        <h5 style="color: red"><strong>Lý do hủy:</strong>
                            ${orderInfo.reason}
                        </h5>
                    </c:if>
            </div>
        <!-- page title area end -->
        <sf:form modelAttribute="searchModel" class="list" action="${base }/admin/orderProduct" method="get">
            <div class="main-content-inner">
                <!-- sales report area start -->
                <div class="card-body"
                     style="display: flex; justify-content: space-between">
                    <div style="display: flex; padding-right: 15px">
                        <sf:input path="pageNumber" type="hidden" id="pageInput" name="pageNumber" class="form-control"/>
<%--                        <sf:input path="keyword" type="text" id="keyword" name="keyword"--%>
<%--                               class="form-control" placeholder="Search"--%>
<%--                               value="${searchModel.keyword }"--%>
<%--                               style="margin-right: 5px; height: 46px;"/>--%>
<%--                        <button type="submit" id="btnSearch" name="btnSearch"--%>
<%--                                value="Search" class="btn btn-flat btn-outline-secondary mb-3">Search--%>
<%--                        </button>--%>
                    </div>
                </div>
            </div>
        </sf:form>
        <!-- Dark table start -->
            <!-- Dark table end -->

            <div class="card order-card">
                <div class="order-header d-flex justify-content-between align-items-center" style="margin: 0 30px; padding-bottom: 15px">
                    <div>
                        <%--                        <h5 class="mb-0">Đơn hàng #${order.id}</h5>--%>
                        <h5>
                            Người tạo: ${orderInfo.createBy}
                        </h5>
                    </div>
                    <div>
                        <h5>Tổng giá trị : ${orderInfo.totalPrice}</h5>
                    </div>
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button"
                                id="dropdownMenuButton" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false"
                                style="background-color: transparent; border-color: transparent;"
                                <c:if test="${orderInfo.paymentDisplayStatus eq 'Đã Giao Hàng'}">disabled</c:if>
                                <c:if test="${orderInfo.paymentDisplayStatus eq 'Đã Hủy'}">disabled</c:if>>
                            <c:choose>
                                <c:when test="${orderInfo.paymentDisplayStatus eq 'Chờ xử lý'}">
                                                    <span class="badge badge-danger"
                                                          style="display: inline-block; width: 100%; height: 100%; font-size: 15px;">Chưa xử lý</span>
                                </c:when>
                                <c:when test="${orderInfo.paymentDisplayStatus eq 'Đang Giao Hàng'}">
                                                    <span class="badge badge-warning"
                                                          style="display: inline-block; width: 100%; height: 100%;  font-size: 15px;">Đang giao hàng</span>
                                </c:when>
                                <c:when test="${orderInfo.paymentDisplayStatus eq 'Đã Giao Hàng'}">
                                                    <span class="badge badge-success"
                                                          style="display: inline-block; width: 100%; height: 100%;  font-size: 15px;">Đã giao hàng</span>
                                </c:when>
                                <c:when test="${orderInfo.paymentDisplayStatus eq 'Đã Hủy'}">
                                                    <span class="badge badge-danger"
                                                          style="display: inline-block; width: 100%; height: 100%;  font-size: 15px;">Hủy đơn</span>
                                </c:when>
                            </c:choose>
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item" href="#" onclick="updateStatus(${orderInfo.code}, 'PENDING')">Chưa xử lý</a>
                            <a class="dropdown-item" href="#" onclick="updateStatus(${orderInfo.code}, 'SHIPPING')">Đang giao hàng</a>
                            <a class="dropdown-item" href="#" onclick="updateStatus(${orderInfo.code}, 'DELIVERED')">Đã giao hàng</a>
                            <a class="dropdown-item reject-order-link" data-order-code="${orderInfo.code}">Hủy đơn hàng</a>
                        </div>
                    </div>
                    <div></div>
                </div>
                <div class="order-body">
                    <div class="single-table"
                         style="margin: 0 30px; padding-bottom: 15px">
                        <div class="table-responsive">
                            <table class="table text-center">

                                <thead class="text-uppercase bg-primary">
                                <tr class="text-white">
                                    <th scope="col">ID</th>
                                    <th scope="col">Mã đơn</th>
                                    <th scope="col">Tên sản phẩm</th>
                                    <th scope="col">Gía sản phẩm</th>
                                    <th scope="col">Số lượng</th>
                                    <th scope="col">Thời gian đặt hàng</th>
                                    <th scope="col">Tổng tiền</th>
<%--                                    <th scope="col">Lựa chọn</th>--%>
                                </tr>
                                </thead>

                                <tbody>

                                <c:forEach items="${orderProductsPage.getContent()}" var="orderProduct"
                                           varStatus="loop">
                                    <tr>
                                        <th scope="row">${loop.index + 1}</th>
                                        <td>${orderProduct.saleOrder.code }</td>
                                        <td>${orderProduct.product.title }</td>
                                        <td>${orderProduct.total / orderProduct.quantity }</td>
                                        <td>${orderProduct.quantity }</td>
                                        <td>${orderProduct.saleOrder.createdDate }</td>
                                        <td>${orderProduct.total} </td>
                                    </tr>
                                </c:forEach>
                                </tbody>

                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Paging -->
            <div class="row">
                <c:if test="${orderProductsPage.totalPages > 1}"> <%-- Chỉ hiển thị phân trang nếu có nhiều hơn 1 trang --%>
                    <div class="float-end align-items-center mt-2">
                        <!-- Nút Trang trước -->
                        <button class="btn btn-outline-primary" onclick="navigateToPage(${orderProductsPage.number - 1})" ${orderProductsPage.first ? 'disabled' : ''}>
                            << Trang trước
                        </button>

                        <!-- Hiển thị thông tin trang hiện tại -->
                        <span class="p-2">${orderProductsPage.number + 1} / ${orderProductsPage.totalPages}</span>

                        <!-- Nút Trang sau -->
                        <button class="btn btn-outline-primary" onclick="navigateToPage(${orderProductsPage.number + 1})" ${orderProductsPage.last ? 'disabled' : ''}>
                            Trang sau >>
                        </button>
                    </div>
                </c:if>
            </div>
    </div>

        <jsp:include page="/WEB-INF/views/admin/rejectModal.jsp"/>
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
        // Chỉ cần đặt giá trị cho trường pageNumber ẩn
        document.getElementById('pageInput').value = pageNumber;
        // Submit form, các trường lọc/sort khác đã được giữ lại bởi Spring Form tags
        document.getElementById('filterForm').submit();
    }
    $(document).ready(function() {
        $('.reject-order-link').click(function(e) {
            e.preventDefault();

            var orderCode = $(this).data('order-code');
            var currentUrl = window.location.pathname + window.location.search;

            // Set the orderId in the modal body
            $('#rejectOrderCodeInput').val(orderCode);
            $('#returnUrlInput').val(currentUrl);

            // Show the modal
            $('#rejectOrderModal').modal('show'); // Use Bootstrap's modal('show') to display the modal
        })
    });

    function updateStatus(orderCode, status) {
        let data = {
            saleOrderCode: orderCode,
            paymentStatus: status
        };

        $.ajax({
            url: '/admin/orderProduct/updateStatus',
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

</script>

</html>
