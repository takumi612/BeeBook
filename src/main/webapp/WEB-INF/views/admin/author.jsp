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
    <title>Tác giả</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="/WEB-INF/views/admin/layouts/css.jsp"/>

</head>
<style>
    .biography {
        display: inline-block;
        max-width: 200px; /* Adjust as needed */
    }
    .full-bio {
        display: none;
    }
    .toggle-bio {
        color: blue;
        cursor: pointer;
        text-decoration: underline;
    }
</style>


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
        <sf:form modelAttribute="searchModel" class="list" action="${base }/admin/author/list" method="get">
            <div class="main-content-inner">
                <!-- sales report area start -->

                <div class="card-body"
                     style="display: flex; justify-content: space-between">
                    <div style="display: flex; padding-right: 15px">
                        <sf:input path="pageNumber" type="hidden" id="pageInput" name="pageNumber" class="form-control"/>
                        <sf:input path="keyword" type="text" id="keyword" name="keyword" class="form-control" placeholder="Search"
                                   value="${searchModel.keyword }"
                                   style="margin-right: 5px; height: 46px; width: 170px;"/>
                        <button type="submit" id="btnSearch" name="btnSearch"
                                value="Search" class="btn btn-flat btn-outline-secondary mb-3">Tìm kiếm
                        </button>
                    </div>
                    <div>
                        <button type="button"
                                class="btn btn-flat btn-outline-secondary mb-3">
                            <a href="${base }/admin/author/add"> Thêm mới tác giả</a>
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
                            <th scope="col">Tên tác giả</th>
                            <th scope="col">Tiểu sử</th>
                            <th scope="col">Ngày tạo</th>
                            <th scope="col">Chức năng</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${authorPage.getContent()}" var="authors"
                                   varStatus="loop">
                            <tr>
                                <th scope="row">${loop.index + 1}</th>
                                <td>${authors.name }</td>
                                <td>
                                    <div class="biography" id="bio-${loop.index}">
                                        <span class="short-bio">${fn:substringBefore(authors.biography, '.')}.</span>
                                        <span class="full-bio" style="display: none;">${authors.biography}</span>
                                        <a href="javascript:void(0);" onclick="toggleBio(${loop.index});" class="toggle-bio">Xem thêm</a>
                                    </div>
                                </td>
                                <td>${authors.createdDate }</td>

                                <td><a class="btn btn-primary"
                                       href="${base }/admin/author/update/${authors.id}" role="button">Sửa</a>
                                    <a class="btn btn-danger" role="button"
                                       href="${base }/admin/deleteAuthor/${authors.id}">Xóa</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>
            <!-- Paging -->
            <div class="row">
                <c:if test="${authorPage.totalPages > 1}"> <%-- Chỉ hiển thị phân trang nếu có nhiều hơn 1 trang --%>
                    <div class="float-end align-items-center mt-2">
                        <!-- Nút Trang trước -->
                        <button class="btn btn-outline-primary" onclick="navigateToPage(${authorPage.number - 1})" ${authorPage.first ? 'disabled' : ''}>
                            << Trang trước
                        </button>

                        <!-- Hiển thị thông tin trang hiện tại -->
                        <span class="p-2">${authorPage.number + 1} / ${authorPage.totalPages}</span>

                        <!-- Nút Trang sau -->
                        <button class="btn btn-outline-primary" onclick="navigateToPage(${authorPage.number + 1})" ${authorPage.last ? 'disabled' : ''}>
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
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        // Lặp qua mỗi checkbox và thiết lập trạng thái mặc định là bật
        $('input[type="checkbox"]').prop('checked', true);
    });

    function toggleBio(index) {
        var bioDiv = document.getElementById('bio-' + index);
        var shortBio = bioDiv.querySelector('.short-bio');
        var fullBio = bioDiv.querySelector('.full-bio');
        var toggleLink = bioDiv.querySelector('.toggle-bio');

        if (fullBio.style.display === 'none') {
            fullBio.style.display = 'inline';
            shortBio.style.display = 'none';
            toggleLink.textContent = 'Ẩn bớt';
        } else {
            fullBio.style.display = 'none';
            shortBio.style.display = 'inline';
            toggleLink.textContent = 'Xem thêm';
        }
    }

    function navigateToPage(pageNumber) {
        document.getElementById('pageInput').value = pageNumber;
        document.getElementById('filterForm').submit();
    }
</script>

</html>
