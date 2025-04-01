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
    <title>Nhà xuất bản</title>
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
        <sf:form modelAttribute="searchModel" class="list" action="${base }/admin/manufacturer/list" method="get">
            <sf:input path="pageNumber" type="hidden" id="pageInput" name="pageNumber" class="form-control"/>
            <div class="main-content-inner">
                <!-- sales report area start -->
                <div class="card-body"
                     style="display: flex; justify-content: space-between">
                    <div style="display: flex; padding-right: 15px">
<%--                        <sf:input path="keyword" type="text" id="keyword" name="keyword" class="form-control" placeholder="Search"--%>
<%--                               value="${searchModel.keyword }"--%>
<%--                               style="margin-right: 5px; height: 46px; width: 170px;"/>--%>
<%--                        <button type="submit" id="btnSearch" name="btnSearch"--%>
<%--                                value="Search" class="btn btn-flat btn-outline-secondary mb-3">Tìm kiếm--%>
<%--                        </button>--%>
                    </div>
                    <div>
                        <button type="button"
                                class="btn btn-flat btn-outline-secondary mb-3">
                            <a href="${base }/admin/manufacturer/add"> Thêm mới nhà xuất bản</a>
                        </button>
                    </div>
                </div>
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
                            <th scope="col">Tên nhà xuất bản</th>
                            <th scope="col">Địa chỉ</th>
                            <th scope="col">Ngày tạo</th>
                            <th scope="col">Chức năng</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${manufacturerPage.getContent()}" var="manufacturers"
                                   varStatus="loop">
                            <tr>
                                <th scope="row">${loop.index + 1}</th>
                                <td>${manufacturers.name }</td>
                                <td>${manufacturers.address }</td>
                                <td>${manufacturers.createdDate }</td>

                                <td><a class="btn btn-primary"
                                       href="${base }/admin/manufacturer/update/${manufacturers.id}" role="button">Sửa</a>
                                    <a class="btn btn-danger" role="button"
                                       href="${base }/deleteManufacturer/${manufacturers.id}">Xóa</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>
            <!-- Paging -->
            <div class="row">
                <c:if test="${manufacturerPage.totalPages > 1}"> <%-- Chỉ hiển thị phân trang nếu có nhiều hơn 1 trang --%>
                    <div class="float-end align-items-center mt-2">
                        <!-- Nút Trang trước -->
                        <button class="btn btn-outline-primary" onclick="navigateToPage(${manufacturerPage.number - 1})" ${manufacturerPage.first ? 'disabled' : ''}>
                            << Trang trước
                        </button>

                        <!-- Hiển thị thông tin trang hiện tại -->
                        <span class="p-2">${manufacturerPage.number + 1} / ${manufacturerPage.totalPages}</span>

                        <!-- Nút Trang sau -->
                        <button class="btn btn-outline-primary" onclick="navigateToPage(${manufacturerPage.number + 1})" ${manufacturerPage.last ? 'disabled' : ''}>
                            Trang sau >>
                        </button>
                    </div>
                </c:if>
            </div>
        </sf:form>
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
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        // Lặp qua mỗi checkbox và thiết lập trạng thái mặc định là bật
        $('input[type="checkbox"]').prop('checked', true);
    });
</script>

</html>
