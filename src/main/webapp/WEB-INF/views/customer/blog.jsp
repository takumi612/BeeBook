<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<!--import JSTL-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- import SPRING-FORM -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Blog</title>
    <link rel="stylesheet"
          href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
          integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
          crossorigin="anonymous"/>
    <link rel="stylesheet" type="text/css" href="${base}/css/details.css">
    <jsp:include page="/WEB-INF/views/customer/layouts/css.jsp"/>
    <%--    <link rel="stylesheet" type="text/css" href="${base}/css/details.css">--%>
</head>
<body>
<main class="container">

    <!--open header-->
    <jsp:include page="/WEB-INF/views/customer/layouts/header.jsp"/>
    <!--close header-->

    <div class="navigation">
        <ul>
            <li><a href="${base }/home">Trang chủ </a></li>

            <li>/</li>

            <li>BLOG</li>
        </ul>
    </div>
    <div class="content">

        <div class="content-products">
            <div class="products-name">
                <a href="#"> ĐÂY LÀ NHỮNG CHIA SẺ , CẢM NHẬN CỦA CHÚNG TÔI ĐÃ ĐƯỢC DIỄN TẢ BẰNG NHỮNG BLOG Ý NGHĨA </a>
            </div>
            <div class="list-product">
                <<c:forEach items="${blogPages.getContent() }" var="blog">
                    <c:if test="${blog.status eq true}">
                        <div class="item">
    
                            <div class="item-images">
                                <a href="${base }/blog/details/${blog.seo}"> <img
                                        src="${base }/upload/${blog.avatar}"
                                        width="100%">
                                </a>
                            </div>
                            <div class="item-content" style="text-align: center;">
                                <a href="${base }/blog/details/${blog.seo}">
                                        ${blog.title } </a>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            <c:if test="${blogPages.totalPages > 1}"> <%-- Chỉ hiển thị phân trang nếu có nhiều hơn 1 trang --%>
                <div class="float-end align-items-center mt-2">
                    <!-- Nút Trang trước -->
                    <button class="btn btn-outline-primary" onclick="navigateToPage(${blogPages.number - 1})" ${blogPages.first ? 'disabled' : ''}>
                        << Trang trước
                    </button>

                    <!-- Hiển thị thông tin trang hiện tại -->
                    <span class="p-2">${blogPages.number + 1} / ${blogPages.totalPages}</span>

                    <!-- Nút Trang sau -->
                    <button class="btn btn-outline-primary" onclick="navigateToPage(${blogPages.number + 1})" ${blogPages.last ? 'disabled' : ''}>
                        Trang sau >>
                    </button>
                </div>
            </c:if>
        </div>
    </div>
    <!--close content-->

    <!--open footer -->
    <jsp:include page="/WEB-INF/views/customer/layouts/footer.jsp"/>
    <!--close footer-->
    <div class="copyright">
        Copyright <i class="far fa-copyright"></i> <a href="#">msic.</a> <a
            href="#">Powered by Haravan</a>
    </div>
</main>
</body>
<script>
    function navigateToPage(pageNumber) {
        document.getElementById('pageInput').value = pageNumber;
        document.getElementById('filterForm').submit();
    }
</script>

<jsp:include page="/WEB-INF/views/customer/layouts/js.jsp"/>
</html>