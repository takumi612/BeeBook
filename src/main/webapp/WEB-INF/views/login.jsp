<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<!-- SPRING FORM -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="${base}/css/register.css">
    <title>Login Form</title>
    <jsp:include page="/WEB-INF/views/common/variables.jsp"></jsp:include>

    <style>
        .toast-success{
            background: linear-gradient(135deg, #00b09b 0%, #96c93d 100%) !important;
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
<div class="container">
    <form method="POST" action="${base }/perform_login">

        <!-- Kiểm tra xem đã login thành công hay không qua biến login_error -->
        <c:if test="${not empty param.login_error }">
            <div class="TB" style="color: red;">
                <c:choose>
                    <c:when test = "${ not empty param.message}">
                        <p>Tài khoản của bạn chưa được kích hoạt hoặc đã bị khóa."</p>
                    </c:when>
                    <c:otherwise>
                        Dang nhap khong thanh cong, thu lai!!!
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>


        <h1>Login</h1>
        <div class="form-control ">
            <!-- bắt buộc name phải để là "username" -->
            <input id="username" type="text" name="username" placeholder="Tên đăng nhập"/>
            <small></small>
            <span></span>
        </div>

        <div class="form-control ">
            <!-- bắt buộc name phải để là "password" -->
            <input id="password" type="password" name="password" placeholder="Mật khẩu"/>
            <small></small>
            <span></span>
        </div>

        <button type="submit" class="btn-submit">Đăng nhập</button>

        <div class="signup-link">
            Chưa có tài khoản <a href="${base }/register">Đăng ký</a>
        </div>
    </form>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Toastify JS -->
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>

<!-- Script để hiển thị Toast -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        <c:if test="${not empty success}">
            Toastify({
                text: "${success}",
                duration: 3000,
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
</script>

</body>
</html>
