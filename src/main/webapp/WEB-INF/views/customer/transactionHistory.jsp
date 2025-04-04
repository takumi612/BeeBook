<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- import SPRING-FORM -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch sử giao dịch</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        .order-card {
            margin-bottom: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .order-header {
            background-color: #f8f9fa;
            border-bottom: 1px solid #dee2e6;
            padding: 15px;
            border-radius: 10px 10px 0 0;
        }
        .order-body {
            padding: 20px;
        }
        .product-img {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 5px;
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <jsp:include page="/WEB-INF/views/customer/layouts/css.jsp"/>

</head>
<body>
<main class="container">
    <jsp:include page="/WEB-INF/views/customer/layouts/header.jsp"/>

    <div class="content">
        <h2 class="mb-4">Lịch sử giao dịch</h2>

        <sf:form modelAttribute="searchModel" style="margin: 10px" class="list" id="filterForm" action="${base }/user/transactionHistory" method="get">
            <sf:input path="pageNumber" type="hidden" id="pageInput" name="pageNumber" value="0"/>
            <div class="main-content-inner">
                <!-- sales report area start -->
                <div class="card-body" style="display: flex; justify-content: space-between">
                    <div class="d-flex align-items-end " style="display: flex; padding-right: 15px; margin-bottom: 20px">
                        <!-- Phần tìm kiếm -->
                        <div class="search-container ">
                            <label class="form-label">Tìm theo Tên</label>
                            <sf:input path="keyword" type="text" id="keyword" name="keyword" class="form-control"
                                   placeholder="Search" value="${searchModel.keyword}"/>
                        </div>

                        <span style="margin-right: 5px; height: 46px; width: 10px;" ></span>

                        <!-- Phần filter theo ngày -->
                            <div class=" " >
                                <label for="startDate" class="form-label">From Date</label>
                                <sf:input path="startDate" type="date" class="form-control" id="startDate" name="startDate"/>
                            </div>
                            <span style="margin-right: 5px; height: 46px; width: 10px;" ></span>

                            <div class=" ">
                                <label for="endDate" class="form-label">To Date</label>
                                <sf:input path="endDate" type="date" class="form-control" id="endDate" name="endDate"/>
                            </div>

                        <span style="margin-right: 5px; height: 46px; width: 10px;" ></span>

                        <%--Filter Trạng thái--%>
                        <div class="search-container  " >
                            <label for="statusFilter" class="form-label">Trạng thái đơn hàng</label>
                            <sf:select path="orderStatus" class="form-control" id="statusFilter" name="status" style="height: 46px">
                                <sf:option value="">Tất cả trạng thái</sf:option>
                                <sf:option value="Chờ xử lý">Chưa xử lý</sf:option>
                                <sf:option value="Đang Giao Hàng">Đang giao hàng</sf:option>
                                <sf:option value="Đã Giao Hàng">Đã giao hàng</sf:option>
                                <sf:option value="Đã Hủy">Hủy đơn</sf:option>
                            </sf:select>
                        </div>

                        <span style="margin-right: 5px; height: 46px; width: 10px;" ></span>

                        <div>
                            <label for="sortByFilter" class="form-label">Sắp xếp</label>
                            <sf:select path="sortBy" class="form-control" id="sortByFilter" name="sortBy" style="height: 46px">
                                <sf:option value="createdDate">Theo Ngày</sf:option>
                                <sf:option value="totalPrice">Theo Giá</sf:option>
                            </sf:select>
                        </div>

                        <span style="margin-right: 5px; height: 46px; width: 10px;" ></span>

                        <div>
                            <label for="sortDirectionFilter" class="form-label"></label>
                            <sf:select path="sortDirection" class="form-control" id="sortDirectionFilter" name="sortDirection" style="height: 46px">
                                <sf:option value="desc">Giảm dần</sf:option>
                                <sf:option value="asc">Tăng dần</sf:option>
                            </sf:select>
                        </div>

                        <span style="margin-right: 5px; height: 46px; width: 10px;" ></span>

                        <button type="submit" id="btnSearch" name="btnSearch" value="Search"
                                class="btn btn-outline-secondary  ">Search</button>
                    </div>
                </div>
            </div>
        </sf:form>

        <a href="${base }/user/transactionHistory" >
            <button class ="btn btn-outline-secondary" style="margin: 10px;" >
                Clear
            </button>
        </a>

        <c:if test="${empty orderPage}">
            <div class="alert alert-info">
                Bạn chưa có giao dịch nào.
            </div>
        </c:if>

        <c:forEach items="${orderPage.getContent()}" var="order">
            <div class="card order-card">
                <div class="order-header d-flex justify-content-between align-items-center">
                    <div>
<%--                        <h5 class="mb-0">Đơn hàng #${order.id}</h5>--%>
                        <h5>
                            <fmt:formatDate value="${order.getCreatedTimestamp()}" pattern="dd/MM/yyyy HH:mm:ss" />
                        </h5>
                    </div>
                    <div>Cách thanh toán:order.createdDatea
                            <c:if test="${not empty order.paymentType}">
                                ${order.paymentDisplayType}
                            </c:if>
                    </div>
                    <div>
                        <c:choose>
                            <c:when test="${order.paymentDisplayStatus eq 'Chờ xử lý'}">
                                                    <span class="badge badge-danger"
                                                          style="display: inline-block; width: 100%; height: 100%; font-size: 15px;">Chưa xử lý</span>
                            </c:when>
                            <c:when test="${order.paymentDisplayStatus eq 'Đang Giao Hàng'}">
                                                    <span class="badge badge-warning"
                                                          style="display: inline-block; width: 100%; height: 100%;  font-size: 15px;">Đang giao hàng</span>
                            </c:when>
                            <c:when test="${order.paymentDisplayStatus eq 'Đã Giao Hàng'}">
                                                    <span class="badge badge-success"
                                                          style="display: inline-block; width: 100%; height: 100%;  font-size: 15px;">Đã giao hàng</span>
                            </c:when>
                            <c:when test="${order.paymentDisplayStatus eq 'Đã Hủy'}">
                                                    <span class="badge badge-danger"
                                                          style="display: inline-block; width: 100%; height: 100%;  font-size: 15px;">Hủy đơn</span>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
                <div class="order-body">
                    <h6 class="mb-3">Chi tiết đơn hàng:</h6>
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th scope="col">Sản phẩm</th>
                                <th scope="col" class="text-center">Đơn giá</th>
                                <th scope="col" class="text-center">Số lượng</th>
                                <th scope="col" class="text-center">Thành tiền</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${order.saleOrderProducts}" var="item">
                                <tr>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <a href="${base }/product/${product.id}">
                                                <img src="${base }/upload/${item.product.avatar}" alt="${item.product.title}" class="product-img me-3">
                                            </a>
                                            <div>
                                                <h6 class="mb-0">${item.product.title}</h6>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="text-center">
                                        <fmt:formatNumber value="${item.product.price}" type="number" maxFractionDigits="0" groupingUsed="true"/> VND
                                    </td>
                                    <td class="text-center">${item.quantity}</td>
                                    <td class="text-center">
                                        <fmt:formatNumber value="${item.product.price * item.quantity}" type="number" maxFractionDigits="0" groupingUsed="true"/> VND
                                    </td>
                                </tr>
                            </c:forEach>
                                <tr>
                                    <td colspan="3" class="text-end fw-bold">Tổng cộng:</td>
                                    <td class="text-end fw-bold">
                                        <fmt:formatNumber value="${order.totalPrice}" type="number" maxFractionDigits="0" groupingUsed="true"/> VND
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <!-- Phân trang -->
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
</main>

<script type="text/javascript">
    function navigateToPage(pageNumber) {
        document.getElementById('pageInput').value = pageNumber;
        document.getElementById('filterForm').submit();
    }
</script>
</body>
</html>