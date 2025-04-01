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
    <title>Blog</title>
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
        <
        <jsp:include page="/WEB-INF/views/admin/layouts/title.jsp"/>
        <!-- page title area end -->
        <sf:form modelAttribute="searchModel" class="list" action="${base }/admin/blog" method="get">
            <div class="main-content-inner">
                <!-- sales report area start -->
                <div class="card-body"
                     style="display: flex; justify-content: space-between">
                    <div style="display: flex; padding-right: 15px">
                        <sf:input path="pageNumber" type="hidden" id="pageInput" name="pageNumber" class="form-control"/>
                        <sf:input path="keyword" type="text" id="keyword" name="keyword" class="form-control" placeholder="Tìm kiếm"
                               value="${searchModel.keyword }"
                               style="margin-right: 5px; height: 46px;"/>
                        <sf:select path="categoryId" class="form-control" name="categoryId" id="categoryId"
                                style="margin-right: 5px; height: 46px;">
                            <c:forEach items="${categoriesBlog}" var="category">
                                <sf:option value="${category.id }">${category.name }</sf:option>
                            </c:forEach>
                        </sf:select>
                        <button type="submit" id="btnSearch" name="btnSearch"
                                value="Search" class="btn btn-flat btn-outline-secondary mb-3">Tìm kiếm
                        </button>
                    </div>
                    <div>
                        <button type="button"
                                class="btn btn-flat btn-outline-secondary mb-3">
                            <a href="${base }/admin/addBlog"> Thêm mới Blog</a>
                        </button>
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
                            <th scope="col">Tiêu đề</th>
                            <%--                            <th scope="col">Trạng thái</th>--%>
                            <th scope="col">Ngày tạo</th>
                            <th scope="col">Hình ảnh</th>
                            <th scope="col">Chức năng</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${blogPage.getContent()}" var="blog" varStatus="loop">
                            <c:if test="${blog.status eq true}">
                                <tr>
                                    <th scope="row">${loop.index + 1}</th>
                                    <td>${blog.title }</td>
                                    <td>${blog.createdDate }</td>

                                    <td><img src="${base }/upload/${blog.avatar}"
                                             width="100" height="100" alt=""></td>
                                    <td><a class="btn btn-primary"
                                           href="${base }/admin/addBlog/${blog.id}" role="button">Sửa</a>
                                        <a class="btn btn-danger" role="button"
                                           href="${base }/admin/deleteBlog/${blog.id}">Xóa</a></td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>

        <div class="row">
            <c:if test="${blogPage.totalPages > 1}"> <%-- Chỉ hiển thị phân trang nếu có nhiều hơn 1 trang --%>
                <div class="float-end align-items-center mt-2">
                    <!-- Nút Trang trước -->
                    <button class="btn btn-outline-primary" onclick="navigateToPage(${blogPage.number - 1})" ${blogPage.first ? 'disabled' : ''}>
                        << Trang trước
                    </button>

                    <!-- Hiển thị thông tin trang hiện tại -->
                    <span class="p-2">${blogPage.number + 1} / ${blogPage.totalPages}</span>

                    <!-- Nút Trang sau -->
                    <button class="btn btn-outline-primary" onclick="navigateToPage(${blogPage.number + 1})" ${blogPage.last ? 'disabled' : ''}>
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Toastify JS -->
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>

<script type="text/javascript">
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
            className: "toast-success",
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
