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
    <title>BeeBooks</title>

    <jsp:include page="/WEB-INF/views/customer/layouts/css.jsp"/>
    <link rel="stylesheet" type="text/css" href="${base}/css/checkOrder.css">
</head>
<body>
<main class="container">

    <jsp:include page="/WEB-INF/views/customer/layouts/header.jsp"/>
    
    <form id="checkOrderForm" action="${base }/checkOrder" method="get" >

        <div class="check-body">
            <div class="container-content">
                <h3>Tra cứu đơn hàng</h3>
                <div class="form-group">
                    <input type="number" id="code" name="code" placeholder="Nhập mã đơn hàng" >
                </div>

                <div class="form-group">
                    <button type="submit" class="sub" id="checkOrderBtn">Tra cứu đơn hàng</button>
                </div>

                <div class="showOrder">
                    <div id="orderStatus"></div>
                </div>
            </div>
        </div>
    </form>

    <%--close content --%>
    <jsp:include page="/WEB-INF/views/customer/layouts/footer.jsp"/>
    <div class="copyright">
        Copyright <i class="far fa-copyright"></i> <a href="#">msic.</a> <a
            href="#">Powered by Haravan</a>
    </div>
</main>

</body>
<jsp:include page="/WEB-INF/views/customer/layouts/js.jsp"/>
<script type="text/javascript">
    document.getElementById('checkOrderBtn').addEventListener('click', function() {
        event.preventDefault();
        var code = document.getElementById('code').value;
        var orderStatusDiv = document.getElementById('orderStatus');

        fetch('${base}/checkOrder/' + code)
            .then(response => response.json())
            .then(data => {
                if (data.error) {
                    orderStatusDiv.innerHTML = '<p>' + data.error + '</p>';
                } else if (data.success) {
                    orderStatusDiv.innerHTML = '<p>' + data.success + '</p>';
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });

</script>

</html>