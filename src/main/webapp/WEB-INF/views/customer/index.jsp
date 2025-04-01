<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<!-- SPRING FORM -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>BeeBooks</title>

    <style>
        .toast-success{
            background: linear-gradient(135deg, #00b09b 0%, #96c93d 100%) !important;
            border-radius: 10px !important;
            padding: 30px !important;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2) !important;
            font-weight: 500 !important;
            position: fixed !important;
            top: 20px !important;
            right: 20px !important;
            margin: 0 !important;
            z-index: 9999 !important;
        }
    </style>

    <jsp:include page="/WEB-INF/views/customer/layouts/css.jsp"/>
</head>
<body>

<main class="container">
    <jsp:include page="/WEB-INF/views/customer/layouts/header.jsp"/>

    <%--open content --%>
    <div class="content">
        <!--banner-->
        <%--        <img src="${base }/img/bannerBooks.png" width="100%"--%>
        <%--             style="max-width: 85%; height: auto;max-height: 500px; display: block; margin: 0 auto;">--%>
        <div class="banner-container">
            <img src="${base }/img/bannerBooks.png" width="100%" alt="Banner 1" class="slide">
            <img src="${base }/img/slide_1.png" width="100%" alt="Banner 2" class="slide">
            <img src="${base }/img/slide_2.png" width="100%" alt="Banner 3" class="slide">
            <img src="${base }/img/slide_3.png" width="100%" alt="Banner 4" class="slide">
            <img src="${base }/img/slide_4.png" width="100%" alt="Banner 5" class="slide">
            <button class="prev" onclick="moveSlide(-1)">❮</button>
            <button class="next" onclick="moveSlide(1)">❯</button>
        </div>
        <!--open new item-->

        <div class="products container my-4">
            <div class="products-name">
                <a href="#"> SẢN PHẨM </a>
            </div>

            <div class="list-product" style="flex-wrap: wrap; justify-content: space-between">
                
                <sf:form modelAttribute="searchModel" id="filterForm">
                    <sf:input type="hidden" path="pageNumber" name="pageNumber" id="pageInput" class="form-control" value="0"/>
                </sf:form>
                
                <c:forEach items="${productsPage.getContent()}" var="product">
                    <c:if test="${product.status eq true}">
                        <div class="item">

                            <div class="item-images">
                                <a href="${base }/product/${product.id}"> <img
                                        src="${base }/upload/${product.avatar}"
                                        width="50%" height="80%" >
                                </a>
                            </div>
                            <div class="item-content">
                                <a href="${base }/product/${product.id}">
                                        ${product.title}</a>
                                <div class="price" id="price${product.id}">
                                    <fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0" groupingUsed="true"/> VND
                                </div>

                            </div>
                        </div>

                        <script>
                            // Lấy ngày hiện tại
                            var currentDate = new Date();

                            // Chuyển đổi ngày bắt đầu và kết thúc từ dạng string sang đối tượng Date
                            var promotionStartDate = new Date("${product.promotion.startDate}");
                            var promotionEndDate = new Date("${product.promotion.endDate}");

                            // So sánh ngày hiện tại với ngày bắt đầu và kết thúc của chương trình khuyến mãi
                            var isInPromotion = currentDate >= promotionStartDate && currentDate <= promotionEndDate;

                            if (isInPromotion) {
                                    // Hiển thị giá khuyến mãi
                                    document.getElementById("price${product.id}").innerHTML = "<fmt:formatNumber value="${product.price - product.price*product.promotion.percent/100}" type="number" maxFractionDigits="0" groupingUsed="true"/> VND";
                                    document.getElementById("price${product.id}").style.color = "red";
                            }
                        </script>
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
        
        <div class="about">
            <div class="about-us">
                <a href="${base }/introduction"> 
                    <img src="${base }/img/qrcode.png" width="100%">
                </a>
                <h3 class="about-title">ABOUT US</h3>
                <div class="about-button">
                    <a href="${base }/introduction"> XEM NGAY </a>
                </div>
            </div>
            <div class="about-us">

                <p>BeeBooks là nhà sách được thành lập vào ngày 11/05/2019 với mục tiêu cung cấp các thể loại sách và
                    bảo tồn những giá trị đọc của tất cả mọi người.
                </p>

                <p>Trong suốt quãng thời gian 5 năm hoạt động, BeeBooks đã trở thành một địa chỉ đáng tin cậy trong lĩnh
                    vực bán sách, đặc biệt là sách cũ, với một sứ mệnh tôn vinh và bảo tồn giá trị của văn hóa đọc.
                    .
                </p>

                <p>Đặc biệt, phần lớn sản phẩm của nhà sách là sách cũ, với một tập hợp đa dạng các đầu sách
                    từ những thập kỷ trước đến những tác phẩm mới nhất. Chúng tôi tin rằng sách cũ mang lại
                    không chỉ giá trị về nội dung mà còn là giá trị về lịch sử và tính cổ điển, đồng thời giúp tạo ra
                    những trải nghiệm đọc sách độc đáo và đặc biệt cho độc giả.
                </p>

                <p>BeeBooks tự hào là nơi lưu giữ và cung cấp cho mọi người những tựa sách phong phú và đa dạng, từ
                    sách giáo khoa, sách kinh tế, sách ngoại ngữ, sách thiếu nhi đến văn học Việt Nam và văn học nước
                    ngoài.
                </p>

                <p>Hiện tại BeeBooks hoạt động và phát triển trên khắp các cả nước
                    thông qua các kênh bán hàng như Facebook, Website....Các bạn có thể đặt hàng và liên hệ với chúng
                    tôi qua
                    các kênh trên như sau:
                </p>

                <p>
                    Instagram: BeeBooks.VN
                </p>

                <p>
                    Facebook: BeeBooks - Cửa hàng sách
                </p>

                <p>
                    Email: beebookvn123@gmail.com.vn
                </p>

                <p>
                    Số điện thoại liên lạc: 0968769276 hoặc 0382556065
                </p>

                <p>
                    Địa chỉ: 180 Đ. Tây Mỗ, Tây Mỗ, Nam Từ Liêm, Hà Nội.
                </p>
            </div>
        </div>
        <!--close about us-->

    </div>
    <!--close content-->

    <%--close content --%>
    <jsp:include page="/WEB-INF/views/customer/layouts/footer.jsp"/>
    <%--    <div class="copyright">--%>
    <%--        Copyright <i class="far fa-copyright"></i> <a href="#">msic.</a> <a--%>
    <%--            href="#">Powered by Haravan</a>--%>
    <%--    </div>--%>
</main>
<!-- Script để hiển thị Toast -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Toastify JS -->
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        <c:if test="${not empty success}">
        Toastify({
            text: "${success}",
            duration: 7000,
            gravity: "top",
            position: "right",
            backgroundColor: "linear-gradient(135deg, #00b09b 0%, #96c93d 100%)",
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
</body>
<jsp:include page="/WEB-INF/views/customer/layouts/js.jsp"/>
</html>