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
    <title>Giỏ Hàng - Msic</title>
    <link rel="stylesheet"
          href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
          integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
          crossorigin="anonymous"/>

    <jsp:include page="/WEB-INF/views/customer/layouts/css.jsp"/>
    <link rel="stylesheet" type="text/css" href="${base}/css/cart.css">
    <style>
        #paymentLink {
            text-decoration: none;
            color: #ff5f17;
            display: inline-block;
            text-align: center;
        }
        .error-message {
            color: red;
            font-size: 13px;
            margin-top: 4px;
        }
        .toast-error{
            background: linear-gradient(135deg, #b08d00 0%, #c93d62 100%) !important;
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
<main class="container">

    <!--open header-->
    <jsp:include page="/WEB-INF/views/customer/layouts/header.jsp"/>
    <!--close header-->

    <div class="navigation">
        <ul>
            <li><a href="${base }/home">Trang chủ </a></li>

            <li>/</li>

            <li>Giỏ hàng</li>
        </ul>
    </div>
    <div class="content">
        <h3>Giỏ hàng của bạn</h3>
        <!-- start shopping cart table-->
        <div class="cart-table">
            <table id="table">
                <thead>
                <tr>
                    <th scope="col" class="border-0 bg-light">
                        <div class="py-2 text-uppercase">Sản phẩm</div>
                    </th>
                    <th scope="col" class="border-0 bg-light">
                        <div class="py-2 text-uppercase">Giá</div>
                    </th>
                    <th scope="col" class="border-0 bg-light">
                        <div class="py-2 text-uppercase">Số lượng</div>
                    </th>
                    <th scope="col" class="border-0 bg-light">
                        <div class="py-2 text-uppercase">Tổng số</div>
                    </th>
                    <th scope="col" class="border-0 bg-light">
                        <div class="py-2 text-uppercase">Xóa</div>
                    </th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${cart.cartItems }" var="ci">
                    <tr>
                        <th scope="row" class="border-0">
                            <div class="p-2">
                                <img src="${ci.productPictures}" alt="" width="70"
                                     class="img-fluid rounded shadow-sm">
                                <div class="ml-3 d-inline-block align-middle">
                                    <h5 class="mb-0">
                                        <a href="#" class="text-dark "> ${ci.productName } </a>
                                    </h5>

                                </div>
                            </div>
                        </th>

                        <td class="border-0">
                            <strong>
                                <fmt:formatNumber value="${ci.priceUnit}" type="number" maxFractionDigits="0" groupingUsed="true"/> VND
                            </strong>
                        </td>
                        <td class="border-0">
                            <button type="button" onclick="AddQuantityCart('${base }',${ci.productId })"
                                    class="cong">+
                            </button>
                            <strong>
                                <span id="quantity_${ci.productId }">${ci.quantity }</span>
                            </strong>

                            <button type="button" onclick="MinusQuantityCart('${base }',${ci.productId })" class="tru">-
                            </button>
                        </td>
                        <td class="border-0" >
                            <strong >
                                <span id="product_total_price_${ci.productId }">
                                        <fmt:formatNumber value="${ci.priceUnit * ci.quantity}" type="number" maxFractionDigits="0" groupingUsed="true"/> VND
                                </span>
                            </strong>
                        </td>
                        <td class="border-0">
                            <a href="${base }/cart/remove/${ci.productId}" class="text-dark">
                                <i class="fa fa-trash" id="card-icon"></i>
                            </a>
                        </td>
                        <td class="border-0">
                            <a type="button" class="a-update"
                               href="${base }/cart/view">Cập nhật</a>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

        </div>


        <!-- End -->
        <sf:form action="${base}/cart/checkout" method="post" modelAttribute="customerDto">
            <div>
                <div class="content-desc">
                    <div class="content-desc-p1">
                        <div class="note">
                            <h5>Ghi chú đơn hàng</h5>
                            <sf:textarea path="note" rows="8" cols="40" placeholder="Ghi chú"/>
                        </div>

                        <div class="sidebox">
                            <h5>Chính sách mua hàng</h5>
                            <ul>
                                <li><i class="fas fa-long-arrow-alt-right"></i>Sản phẩm
                                    được đổi 1 lần duy nhất, không hỗ trợ trả.
                                </li>
                                <li><i class="fas fa-long-arrow-alt-right"></i>Sản phẩm
                                    còn đủ tem mác, chưa qua sử dụng.
                                </li>
                                <li><i class="fas fa-long-arrow-alt-right"></i>Sản phẩm
                                    nguyên giá được đổi trong 30 ngày trên toàn hệ thống.
                                </li>
                            </ul>
                        </div>
                    </div>

                </div>

                <div class="content-desc">
                    <div class="form-row" style="box-sizing: border-box;">
                        <div class="form-group">
                            <h4>Thông tin khách hàng</h4>
                        </div>

                        <div class="form-group">
                            <label for="customerName">Họ và tên</label><br>
                            <sf:input path="customerName" cssClass="form-control" placeholder="Name" />
                            <sf:errors path="customerName" cssClass="error-message" />
                        </div>
                        <div class="form-group">
                            <label for="customerEmail">Địa chỉ email</label><br>
                            <sf:input path="customerEmail" cssClass="form-control" placeholder="Enter email" />
                            <sf:errors path="customerEmail" cssClass="error-message" />
                        </div>
                        <div class="form-group">
                            <label for="customerPhone">Số điện thoại</label><br>
                            <sf:input path="customerPhone" cssClass="form-control" placeholder="Phone" />
                            <sf:errors path="customerPhone" cssClass="error-message" />
                        </div>
                        <div class="form-group">
                            <label for="customerAddress">Địa chỉ</label><br>
                            <sf:input path="customerAddress" cssClass="form-control" placeholder="Address" />
                            <sf:errors path="customerAddress" cssClass="error-message" />
                        </div>
                    </div>

                    <div class="content-desc-p2">
                        <div class="p2-title">
                            <h4>Thông tin đơn hàng</h4>
                        </div>
                        <div class="p2-total">
                            <p>
                                Tổng tiền :
                                <span class="total-price" id="total_price">
                                    <fmt:formatNumber value="${cart.totalPrice}" type="number" maxFractionDigits="0" groupingUsed="true"/> VND
                                </span>
                            </p>
                        </div>
                        <div class="p2-text">
                            <p>
                                Khi click đặt hàng, hệ thống sẽ mặc định bạn thanh toán khi nhận hàng
                            </p>
                        </div>
                        <div class="p2-action">
                            <c:if test="${not empty cart.cartItems }">
                                <button type="submit" class="thanhtoan" id="cartBtn">
                                    ĐẶT HÀNG
                                </button>
                            </c:if>
                            <p>
                                <a href="${base }/home"><i class="fas fa-undo"></i>Tiếp
                                    tục mua hàng</a>
                            </p>
                        </div>
                        <div class="showOrder">
                            <div id="orderStatus"></div>
                        </div>
                    </div>
                </div>
            </div>
        </sf:form>
    </div>

    <!--close content-->

    <!--open footer -->
    <jsp:include page="/WEB-INF/views/customer/layouts/footer.jsp"/>
    <!--close footer-->
    <div class="copyright">
        Copyright <i class="far fa-copyright"></i> <a href="#">beebooks.</a> <a
            href="#">Powered by Haravan</a>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Toastify JS -->
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        <c:if test="${not empty error}">
        Toastify({
            text: "${error}",
            duration: 7000,
            gravity: "top",
            position: "right",
            backgroundColor: "linear-gradient(135deg, #b08d00 0%, #c93d62 100%)",
            className: "toast-error",
            stopOnFocus: true,
            offset: {
                x: 20,
                y: 20
            }
        }).showToast();
        </c:if>
    });
</script>
</body>

<jsp:include page="/WEB-INF/views/customer/layouts/js.jsp"/>
<script type="text/javascript">
    function cong() {
        var t = document.getElementById("textbox").value;
        document.getElementById("textbox").value = parseInt(t) + 1;
    }

    function tru() {
        var t = document.getElementById("textbox").value;
        document.getElementById("textbox").value = parseInt(t) - 1;
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Toastify JS -->
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>

<script type="text/javascript">

</script>
</html>