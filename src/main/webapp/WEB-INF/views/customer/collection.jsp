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
        <title>Tất cả sản phẩm</title>
        <link rel="stylesheet"
              href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
              integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
              crossorigin="anonymous"/>

        <jsp:include page="/WEB-INF/views/customer/layouts/css.jsp"/>
        <link rel="stylesheet" type="text/css" href="${base}/css/collection.css">
        <style>
            /* Adjust the input field */
            .rounded-input {
                border-radius: 15px;
                padding: 10px 15px;
                border: 1px solid #ced4da;
                width: 30%;
            }

            /* Adjust the search button */
            .btn-search {
                border-radius: 15px;
                padding: 10px 20px;
                background-color: #007bff; /* Set your desired background color */
                color: #fff;
                transition: background-color 0.3s;
                border: none;
            }

            .btn-search:hover {
                background-color: #0056b3; /* Change to desired hover color */
            }

            /* Adjust the search icon */
            .btn-search i {
                margin-right: 5px;
            }

        </style>
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
                <li><a href="#">Danh mục</a></li>
                <li>/</li>
                <li>Tất cả sản phẩm</li>
            </ul>
        </div>
        <!-- open content -->
        <div class="content product-list">
            <sf:form modelAttribute="searchModel" id = "filterForm" class="list" action="${base }/collection" method="post" style="display: flex;">
                <div class="content-bar">
                    <sf:input type="hidden" path="pageNumber" name="pageNumber" id="pageInput" class="form-control" value="0"/>
                    <sf:input type="hidden" path="keyword" name="keyword" id="keyword" class="form-control" value=""/>
                    <sf:input type="hidden" path="categoryId" name="categoryId" id="categoryId" class="form-control" value="0"/>

                    <div class="title-bar">
                        <div class="title-bar-p1">Danh mục sản phẩm</div>
                        <div class="filter-price">
                            <button type="button" onclick="navigateToCategories(0)" class="btn-category" style="background: white; border: none;">
                                All
                            </button>
                            <c:forEach  items="${categories}" var="category">
                            <button type="button" class="btn-category" onclick="navigateToCategories(${category.id})" style="background: white; border: none;">
                                ${category.name }
                            </button>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </sf:form>


                <div class="content-collection">
                    <div class="collection-title">
                        <div class="all-coll">
                            <h3>Tất cả sản phẩm</h3>
                        </div>
                    </div>

                    <div class="list-product" id="list-product" style="flex-wrap: wrap;">

                        <c:forEach items="${productsPage.getContent()}" var="product">
                            <c:if test="${product.status eq true}">
                                <div class="item" style="margin-left: 30px;">

                                    <div class="item-images">
                                        <a href="${base }/product/${product.id}"> <img
                                                src="${base }/upload/${product.avatar}"
                                                width="100%">
                                        </a>
                                    </div>
                                    <div class="item-content">
                                        <a href="${base }/product/${product.id}">
                                                ${product.title } </a>
                                        <div class="price" id="price${product.id}">
                                            <fmt:setLocale value="vi_VN"/>
                                            <fmt:formatNumber value="${product.price}" type="currency"/>
                                        </div>
                                    </div>
                                </div>
    <%--                            <script>--%>
    <%--                                // Lấy ngày hiện tại--%>
    <%--                                var currentDate = new Date();--%>

    <%--                                // Chuyển đổi ngày bắt đầu và kết thúc từ dạng string sang đối tượng Date--%>
    <%--                                var promotionStartDate = new Date("${product.promotion.startDate}");--%>
    <%--                                var promotionEndDate = new Date("${product.promotion.endDate}");--%>

    <%--                                // So sánh ngày hiện tại với ngày bắt đầu và kết thúc của chương trình khuyến mãi--%>
    <%--                                var isInPromotion = currentDate >= promotionStartDate && currentDate <= promotionEndDate;--%>

    <%--                                if (isInPromotion) {--%>
    <%--                                    if (${product.price} != ${product.price_sale}) {--%>
    <%--                                        // Hiển thị giá khuyến mãi--%>
    <%--                                        document.getElementById("price${product.id}").innerHTML = "<fmt:formatNumber value="${product.price_sale}" type="currency"/>";--%>
    <%--                                        document.getElementById("price${product.id}").style.color = "red";--%>
    <%--                                    }else {--%>
    <%--                                        // Hiển thị giá thông thường--%>
    <%--                                        document.getElementById("price${product.id}").innerHTML = "<fmt:formatNumber value="${product.price}" type="currency"/>";--%>
    <%--                                        document.getElementById("price${product.id}").style.color = "black";--%>
    <%--                                    }--%>
    <%--                                }--%>
    <%--                            </script>--%>
                            </c:if>
                        </c:forEach>
                    </div>
                    <c:if test="${productsPage.totalPages > 1}"> <%-- Chỉ hiển thị phân trang nếu có nhiều hơn 1 trang --%>
                        <div class="float-end align-items-center mt-2">
                            <!-- Nút Trang trước -->
                            <button class="btn btn-outline-primary" onclick="navigateToPage(${productsPage.number - 1})" ${productsPage.first ? 'disabled' : ''}>
                                << Trang trước
                            </button>

                            <!-- Hiển thị thông tin trang hiện tại -->
                            <span class="p-2">${productsPage.number + 1} / ${productsPage.totalPages}</span>

                            <!-- Nút Trang sau -->
                            <button class="btn btn-outline-primary" onclick="navigateToPage(${productsPage.number + 1})" ${productsPage.last ? 'disabled' : ''}>
                                Trang sau >>
                            </button>
                        </div>
                    </c:if>
                </div>
        </div>
        <!--close content-->

        <!--open footer -->
        <jsp:include page="/WEB-INF/views/customer/layouts/footer.jsp"/>
        <!--close footer -->
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
        function navigateToCategories(categoryId) {
            document.getElementById('keyword').value = "";
            document.getElementById('categoryId').value = categoryId;
            document.getElementById('filterForm').submit();
        }
    </script>
    <jsp:include page="/WEB-INF/views/customer/layouts/js.jsp"/>
</html>