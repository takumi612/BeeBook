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
    <title>Đơn hàng</title>
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
        <!-- page title area end -->
        <div class="main-content-inner">

            <sf:form modelAttribute="searchModel" class="list" id="filterForm" style="margin: 10px" action="${base }/admin/order" method="get">
                <!-- sales report area start -->
                <sf:input path="pageNumber" type="hidden" name="pageNumber" id="pageInput" value="0" />

                <div class="card-body" style="display: flex; justify-content: space-between">
                    <div class="d-flex align-items-end " style="display: flex; padding-right: 15px">
                        <!-- Phần tìm kiếm -->
                        <div class="search-container " style="margin-right: 20px; width: 170px;">
                            <label for="keyword">Tìm kiếm</label>
                            <sf:input path="keyword" type="text" id="keyword" name="keyword" class="form-control"
                                                                placeholder="Search"/>
                        </div>
                        <jsp:useBean id="now" class="java.util.Date" />
                        <!-- Phần filter theo ngày -->
                        <form id="dateRangeFilterForm" method="get" class="d-flex align-items-end">
                            <div class=" " >
                                <label for="startDate" class="form-label">From Date</label>
                                <fmt:formatDate value='${now}' pattern='yyyy-MM-dd'/>
                                <sf:input path="startDate" type="date" class="form-control" id="startDate" name="startDate" value = "${formateDate}" />
                            </div>
                            <span style="margin-right: 5px; width: 10px;" ></span>

                            <div class=" ">
                                <label for="endDate" class="form-label">To Date</label>
                                <sf:input path="endDate" type="date" class="form-control" id="endDate" name="endDate"/>
                            </div>
                        </form>

                            <span style="margin-right: 5px; width: 10px;" ></span>

<%--                        Filter Trạng thái--%>
                            <div class="search-container  " >
                                <label for="statusFilter" class="form-label">Trạng thái đơn hàng</label>
                                <sf:select path="orderStatus" class="form-control" id="statusFilter" name="status" style="height: 46px">
                                    <sf:option value="">Tất cả trạng thái</sf:option>
                                    <sf:option value="PENDING">Chưa xử lý</sf:option>
                                    <sf:option value="SHIPPING">Đang giao hàng</sf:option>
                                    <sf:option value="DELIVERED">Đã giao hàng</sf:option>
                                    <sf:option value="CANCELLED">Hủy đơn</sf:option>
                                </sf:select>
                            </div>

                            <span style="margin-right: 5px; width: 10px;" ></span>

                            <div>
                                <label for="sortByFilter" class="form-label">Sắp xếp</label>
                                <sf:select path="sortBy" class="form-control" id="sortByFilter" name="sortBy" style="height: 46px">
                                    <sf:option value="createdDate">Theo Ngày</sf:option>
                                    <sf:option value="totalPrice">Theo Giá</sf:option>
                                </sf:select>
                            </div>

                        <span style="margin-right: 5px; width: 10px;" ></span>

                            <div>
                                <label for="sortDirectionFilter" class="form-label"></label>
                                <sf:select path="sortDirection" class="form-control" id="sortDirectionFilter" name="sortDirection" style="height: 46px">
                                    <sf:option value="desc">Giảm dần</sf:option>
                                    <sf:option value="asc">Tăng dần</sf:option>
                                </sf:select>
                            </div>

                            <span style="margin-right: 5px; width: 10px;" ></span>

                            <button type="submit" id="btnSearch" name="btnSearch" value="Search"
                                    class="btn btn-outline-secondary  ">Search</button>
                    </div>
                </div>
            </sf:form>

                <a href="${base }/admin/order">
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
                            <th scope="col">ID</th>
                            <th scope="col">Mã đơn</th>

                            <th scope="col">Khách hàng</th>

                            <th scope="col">Phương thức thanh toán</th>
                            <th scope="col">Ngày tạo hóa đơn</th>

                            <th scope="col">Tổng hóa đơn</th>
                            <th scope="col">Trạng thái đơn hàng</th>

                            <th scope="col">Chi tiết</th>
                            <th scope="col">Hủy đơn</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${orderPage.getContent()}" var="orders"
                                   varStatus="loop">
                            <tr>
                                <th scope="row">${loop.index + 1}</th>
                                <td>${orders.code }</td>

                                <td>
                                        ${orders.customerName } <br>
                                        ${orders.customerPhone } <br>
                                        ${orders.customerAddress } <br>
                                        ${orders.customerEmail } <br>
                                </td>
                                <td>
                                        <c:if test="${not empty orders.paymentType}">
                                            ${orders.paymentDisplayType}
                                        </c:if>

                                </td>
                                <td>${orders.createdDate}</td>
                                <td>${orders.totalPrice}</td>
                                <td>
                                    <div class="dropdown">
                                        <button class="btn btn-secondary dropdown-toggle" type="button"
                                                id="dropdownMenuButton" data-toggle="dropdown"
                                                aria-haspopup="true" aria-expanded="false"
                                                style="background-color: transparent; border-color: transparent;"
                                                <c:if test="${orders.paymentDisplayStatus eq 'Đã Giao Hàng'}">disabled</c:if>
                                                <c:if test="${orders.paymentDisplayStatus eq 'Đã Hủy'}">disabled</c:if>>
                                            <c:choose>
                                                <c:when test="${orders.paymentDisplayStatus eq 'Chờ xử lý'}">
                                                    <span class="badge badge-danger"
                                                          style="display: inline-block; width: 100%; height: 100%; font-size: 15px;">Chưa xử lý</span>
                                                </c:when>
                                                <c:when test="${orders.paymentDisplayStatus eq 'Đang Giao Hàng'}">
                                                    <span class="badge badge-warning"
                                                          style="display: inline-block; width: 100%; height: 100%;  font-size: 15px;">Đang giao hàng</span>
                                                </c:when>
                                                <c:when test="${orders.paymentDisplayStatus eq 'Đã Giao Hàng'}">
                                                    <span class="badge badge-success"
                                                          style="display: inline-block; width: 100%; height: 100%;  font-size: 15px;">Đã giao hàng</span>
                                                </c:when>
                                                <c:when test="${orders.paymentDisplayStatus eq 'Đã Hủy'}">
                                                    <span class="badge badge-danger"
                                                          style="display: inline-block; width: 100%; height: 100%;  font-size: 15px;">Hủy đơn</span>
                                                </c:when>
                                            </c:choose>
                                        </button>
                                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                            <a class="dropdown-item" href="#" onclick="updateStatus(${orders.code}, 'PENDING')">Chưa
                                                xử lý</a>
                                            <a class="dropdown-item" href="#" onclick="updateStatus(${orders.code}, 'SHIPPING')">Đang
                                                giao hàng</a>
                                            <a class="dropdown-item" href="#" onclick="updateStatus(${orders.code}, 'DELIVERED')">Đã
                                                giao hàng</a>
                                        </div>
                                    </div>
                                </td>

                                <td>
                                    <a class="btn " href="${base }/admin/orderProduct/${orders.code}"
                                       role="button">
                                        <i class="fas fa-eye"></i>
                                    </a>
                                </td>
                                <td>
                                    <c:if test="${not (orders.paymentDisplayStatus eq 'Đã Hủy') }">
                                        <a class="btn reject-order-link" data-order-code="${orders.code}" role="button">
                                            <i class="fas fa-times"></i>
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
                <c:if test="${orderPage.totalPages > 1}"> <%-- Chỉ hiển thị phân trang nếu có nhiều hơn 1 trang --%>
                    <div class="float-end align-items-center mt-2">
                        <!-- Nút Trang trước -->
                        <button class="btn btn-outline-primary" onclick="navigateToPage(${orderPage.number - 1})" ${orderPage.first ? 'disabled' : ''}>
                            << Trang trước
                        </button>

                        <!-- Hiển thị thông tin trang hiện tại -->
                        <span class="p-2">${orderPage.number + 1} / ${orderPage.totalPages}</span>

                        <!-- Nút Trang sau -->
                        <button class="btn btn-outline-primary" onclick="navigateToPage(${orderPage.number + 1})" ${orderPage.last ? 'disabled' : ''}>
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

<jsp:include page="/WEB-INF/views/admin/rejectModal.jsp"/>

<jsp:include page="/WEB-INF/views/admin/layouts/offset.jsp"/>
<jsp:include page="/WEB-INF/views/admin/layouts/js.jsp"/>
</body>
<script type="text/javascript">
    function updateStatus(orderCode, status) {
        let data = {
            saleOrderCode: orderCode,
            paymentStatus: status
        };

        $.ajax({
            url: '${base }/admin/orderProduct/updateStatus',
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

    function navigateToPage(pageNumber) {
        const startDateString = $('#startDate').val(); // Lấy giá trị từ input trong form chính
        const endDateString = $('#endDate').val();     // Lấy giá trị từ input trong form chính

        // Chỉ xác thực nếu cả hai ngày đều được nhập
        if (startDateString && endDateString) {
            const startDate = new Date(startDateString);
            const endDate = new Date(endDateString);

            // Kiểm tra xem ngày bắt đầu có lớn hơn ngày kết thúc không
            if (startDate > endDate) {
                alert('Ngày bắt đầu (From Date) không thể lớn hơn Ngày kết thúc (To Date). Vui lòng kiểm tra lại.');
                // Quan trọng: Dừng thực thi hàm tại đây, không submit form
                return;
            }
        }
        document.getElementById('pageInput').value = pageNumber;
        document.getElementById('filterForm').submit();
    }
</script>

</html>
