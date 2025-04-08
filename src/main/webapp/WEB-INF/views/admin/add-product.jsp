<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<!--import JSTL-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- import SPRING-FORM -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html class="no-js" lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Add-product</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
        <jsp:include page="/WEB-INF/views/admin/layouts/title.jsp"/>
        <!-- page title area end -->
        <div class="main-content-inner">
            <!-- sales report area start -->
            <div class="form-search">
                <div class="col-12">
                    <div class="card mt-5">

                        <c:if test="${not empty TB }">
                            <div class="alert alert-primary" role="alert">${TB }</div>
                        </c:if>

                        <div class="card-body">
                            <h4 class="header-title">Thêm/Cập nhật sản phẩm</h4>
                            <!-- Dạng 2 :sử dụng spring-form -->
                            <sf:form modelAttribute="product" class="needs-validation"
                                     novalidate="" action="${base }/admin/addProduct"
                                     method="post" enctype="multipart/form-data">

                                <sf:hidden path="id" />
                                <sf:hidden path="createdDate" />
                                <sf:hidden path="createdBy" />

                                <sf:input type="hidden" path="avatar" name="avatar" id="avatar" class="form-control"/>
                                <sf:input type="hidden" path="images" name="productImage" id="productImage" class="form-control"/>


                                <div class="form-row">
                                    <div class="row">
                                        <div class="col-md-4 mb-3">
                                            <label>Danh mục</label>
                                            <sf:select path="categoriesId" class="form-control">
                                                <sf:options items="${categories }" itemValue="id"
                                                            itemLabel="name"/>
                                            </sf:select>
                                            <div class="valid-feedback">Looks good!</div>
                                        </div>

                                        <div class="col-md-4 mb-3">
                                            <label for="validationCustom02">Tên sản phẩm</label>
                                            <sf:input path="title" type="text" class="form-control"
                                                      id="validationCustom02" placeholder="..." required="required"/>
                                            <div class="valid-feedback">Looks good!</div>
                                        </div>

                                        <div class="col-md-4 mb-3">
                                            <label for="validationCustom03">Nhà xuất bản</label>
                                            <sf:select path="manufacturerId" class="form-control">
                                                <sf:options items="${manufacturer }" itemValue="id"
                                                            itemLabel="name"/>
                                            </sf:select>
                                            <div class="valid-feedback">Looks good!</div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-4 mb-3">
                                            <label for="validationCustom04">Tác giả</label>
                                            <sf:select path="authorId" class="form-control">
                                                <sf:options items="${author }" itemValue="id"
                                                            itemLabel="name"/>
                                            </sf:select>
                                            <div class="valid-feedback">Looks good!</div>
                                        </div>

                                        <div class="col-md-4 mb-3">
                                            <label for="validationCustom02">Năm xuất bản</label>
                                            <sf:input path="publicationYear" type="text" class="form-control"
                                                      id="validationCustom06" placeholder="..." required="required"/>
                                            <div class="valid-feedback">Looks good!</div>
                                        </div>

                                        <div class="col-md-4 mb-3">
                                            <label for="validationCustom03">Ngày phát hành</label>
                                            <c:set var="formattedDate" value="" />
                                            <c:if test="${not empty product.releaseDate}">
                                                <fmt:formatDate value="${product.releaseDate}" pattern="yyyy-MM-dd" var="formattedDate" />
                                            </c:if>

                                            <sf:input path="releaseDate" type="date" class="form-control"
                                                      value="${formattedDate}" id="validationCustom03" required="required"/>
                                            <div class="valid-feedback">Looks good!</div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-4 mb-3">
                                            <label for="validationCustom04">Thành tiền</label>
                                            <sf:input path="price" type="number" class="form-control"
                                                      id="validationCustom04" placeholder="..." required="required"/>
                                            <div class="invalid-feedback">Hãy nhập thành tiền sản phẩm!</div>
                                        </div>

                                        <div class="col-md-4 mb-3">
                                            <label for="validationCustom04">Chương trình khuyến mãi</label>
                                            <sf:select path="promotionId" class="form-control" id="promotionSelect">
                                                <sf:options items="${promotion}" itemValue="id" itemLabel="name"/>
                                            </sf:select>
                                            <div class="invalid-feedback">Hãy nhập giá khuyến mãi sản phẩm!</div>
                                        </div>

                                        <div class="col-md-4 mb-3">
                                            <label for="priceSale">Giá khuyến mãi (tính toán)</label>
                                            <input type="text" class="form-control" id="priceSale" readonly>
                                        </div>

                                        <input type="hidden" id="priceSaleHidden" name="price_sale">
                                    </div>


                                    <div class="row">
                                        <div class="col-md-4 mb-3">
                                            <label>Ảnh sản phẩm</label>
                                            <div class="d-flex flex-column">
                                                <!-- Hiển thị ảnh hiện tại nếu có -->
                                                <c:if test="${not empty product.avatar}">
                                                    <div class="mb-2 position-relative" id="current-avatar-container">
                                                        <img src="${base}/upload/${product.avatar}" alt="Ảnh sản phẩm" class="img-thumbnail" style="max-height: 150px;">
                                                    </div>
                                                </c:if>

                                                <!-- Input tải lên ảnh mới -->
                                                <sf:input path="productAvatar" type="file" class="form-control" id="productAvatarInput" onchange="previewAvatar(this)"/>

                                                <!-- Vùng hiển thị xem trước ảnh mới -->
                                                <div id="avatar-preview" class="mt-2" style="display: none;">
                                                    <img src="" id="avatar-preview-img" class="img-thumbnail" style="max-height: 150px;">
                                                </div>

                                                <div class="invalid-feedback">Thêm ảnh sản phẩm!</div>
                                            </div>
                                        </div>

                                        <div class="col-md-8 mb-3">
                                            <label>Danh sách ảnh sản phẩm</label>
                                            <div class="d-flex flex-column">
                                                <!-- Hiển thị danh sách ảnh hiện tại -->
                                                <c:if test="${not empty product.images}">
                                                    <div class="mb-2" id="product-images-container">
                                                        <div class="d-flex flex-wrap">
                                                            <c:forEach items="${product.images}" var="image" varStatus="loop">
                                                                <div class="position-relative m-1 product-image-item" id="img-${loop.index}">
                                                                    <img src="${base}/upload/${image.path}" class="img-thumbnail" style="height: 100px; width: 100px; object-fit: cover;" alt=""/>
                                                                    <button type="button" class="btn btn-sm btn-danger position-absolute" style="top: 0; right: 0;"
                                                                            onclick="removeProductImage('${image.path}', 'img-${loop.index}')">
                                                                        <i class="fa fa-times"></i>
                                                                    </button>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </c:if>
                                                <!-- Input tải lên ảnh mới -->
                                                <sf:input path="productPictures" type="file" class="form-control" id="productPicturesInput"
                                                          multiple="multiple" onchange="previewProductImages(this)"/>

                                                <!-- Vùng hiển thị xem trước ảnh mới -->
                                                <div id="product-images-preview" class="mt-2 d-flex flex-wrap">
                                                    <!-- Ảnh xem trước sẽ được thêm vào đây bởi JavaScript -->
                                                </div>

                                                <div class="invalid-feedback">Thêm danh sách ảnh sản phẩm!</div>
                                            </div>

                                            <sf:input path="removedImages" type="hidden" id="imagesToDelete" name="imagesToDelete" value=""/>

                                        </div>

                                        <div class="col-md-4 mb-3">
                                            <label for="validationCustom04">Số lượng</label>
                                            <sf:input path="quantity" type="number" class="form-control"
                                                      id="validationCustom04" placeholder="..." required="required"/>
                                            <div class="invalid-feedback">Hãy nhập số lượng sản phẩm!</div>
                                        </div>
                                    </div>

                                </div>
                                <div class="form-row">
                                    <div class="col-md-12 mb-3">
                                        <label for="validationCustom03">Mô tả</label>
                                        <sf:textarea autocomplete="off" path="shortDes" type="text"
                                                     class="form-control" id="validationCustom03"
                                                     placeholder="..." required="required" rows="3"/>
                                        <div class="invalid-feedback">Hãy nhập mô tả!</div>
                                    </div>

                                    <div class="col-md-12 mb-3">
                                        <label for="validationCustom03">Chi tiết sản phẩm</label>
                                        <sf:textarea path="details" type="text"
                                                     class="form-control summernote" id="validationCustom05"
                                                     name="details" placeholder="..." required="required"
                                                     rows="3"/>
                                        <div class="invalid-feedback">Hãy nhập chi tiết sản
                                            phẩm!
                                        </div>
                                    </div>
                                </div>
                                <button class="btn btn-primary" type="submit">
                                    Lưu
                                </button>
                            </sf:form>
                        </div>
                    </div>
                </div>
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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Toastify JS -->
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>


    <!-- internal javascript -->
    <script type="text/javascript">
        $(document).ready(function () {
            $('#validationCustom05').summernote();
        });

        document.addEventListener('DOMContentLoaded', function() {
            const promotionSelect = document.getElementById('promotionSelect');
            const priceSaleInput = document.getElementById('priceSale');
            const priceSaleHidden = document.getElementById('priceSaleHidden');
            const priceInput = document.querySelector('[name="price"]');

            function calculatePriceSale() {
                const price = parseFloat(priceInput.value);
                const selectedOption = promotionSelect.options[promotionSelect.selectedIndex];
                const promotionPercent = parseFloat(selectedOption.text);

                if (!isNaN(price) && !isNaN(promotionPercent)) {
                    const discountAmount = (price * promotionPercent) / 100;
                    const priceSale = price - discountAmount;
                    priceSaleInput.value = priceSale.toFixed(2);
                    priceSaleHidden.value = priceSale.toFixed(2);
                } else {
                    priceSaleInput.value = '';
                    priceSaleHidden.value = '';
                }
            }

            // Tính toán giá khuyến mãi khi thay đổi khuyến mãi
            promotionSelect.addEventListener('change', calculatePriceSale);

            // Tính toán giá khuyến mãi khi nhập giá
            priceInput.addEventListener('input', calculatePriceSale);
        });

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

        $(document).ready(function() {
            // Code hiện tại của bạn vẫn giữ nguyên

            // Các biến toàn cục để theo dõi trạng thái ảnh
            window.imagesToDelete = [];
        });

        // Xem trước ảnh đại diện khi chọn file mới
        function previewAvatar(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    $('#avatar-preview-img').attr('src', e.target.result);
                    $('#avatar-preview').show();
                    // Ẩn ảnh hiện tại nếu có
                    $('#current-avatar-container').hide();
                    // Reset flag xóa avatar
                    $('#removeAvatarFlag').val('false');
                }
                reader.readAsDataURL(input.files[0]);
            }
        }

        // // Xóa ảnh đại diện hiện tại
        // function removeAvatar() {
        //     $('#current-avatar-container').hide();
        //     $('#removeAvatarFlag').val('true');
        //     $('#productAvatarInput').val(''); // Xóa file đã chọn nếu có
        // }

        // Xem trước nhiều ảnh sản phẩm khi chọn files mới
        function previewProductImages(input) {
            var preview = $('#product-images-preview');
            preview.html(''); // Xóa các preview cũ

            if (input.files && input.files.length > 0) {
                for (var i = 0; i < input.files.length; i++) {
                    var reader = new FileReader();
                    reader.onload = (function(idx) {
                        return function(e) {
                            var imgContainer = $('<div class="position-relative m-1 product-image-preview">');
                            var img = $('<img>').attr({
                                'src': e.target.result,
                                'class': 'img-thumbnail',
                                'style': 'height: 100px; width: 100px; object-fit: cover;'
                            });

                            var removeBtn = $('<button type="button" class="btn btn-sm btn-danger position-absolute" style="top: 0; right: 0;">').html('<i class="fa fa-times"></i>');
                            removeBtn.click(function() {
                                $(this).parent().remove();
                            });

                            imgContainer.append(img).append(removeBtn);
                            preview.append(imgContainer);
                        }
                    })(i);
                    reader.readAsDataURL(input.files[i]);
                }
            }
        }

        // Xóa ảnh sản phẩm từ danh sách
        function removeProductImage(imagePath, containerId) {
            // Thêm ID vào danh sách cần xóa
            window.imagesToDelete = window.imagesToDelete || [];
            window.imagesToDelete.push(imagePath);
            $('#imagesToDelete').val(window.imagesToDelete.join(','));
            console.log(window.imagesToDelete);
            // Ẩn hình ảnh khỏi UI
            $('#' + containerId).hide();
        }

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
    </script>

</body>

</html>
