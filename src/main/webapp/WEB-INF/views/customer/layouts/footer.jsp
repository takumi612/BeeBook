<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!--import JSTL-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!--open footer -->
<div class="footer">
	<form action="${base }/subscribe" method="post" modelAttribute="subscribe">
		<div class="contact">
			<div class="register">
				<div class="register-icon">
					<i class="far fa-envelope"></i>
				</div>
				<div class="register-title">Đăng kí nhận tin</div>
			</div>
			<div class="search search-email">
				<input type="email" class="searchTerm"
					placeholder="Nhập email của bạn" id="email" name="email">
				<button type="button" onclick="home('${base}');" class="save-email">Đăng
					kí</button>

			</div>
			<div class="call">
				<div class="call-icon">
					<i class="fas fa-phone-square-alt"></i>
				</div>
				<div class="call-title">
					Hỗ trợ / Mua hàng: <span class="call-phone">0382556065</span>
				</div>
			</div>
		</div>

		<div id="TB_AJAX" class="error-message"
			style="text-align: center; margin-top: 15px; color: #766b6b; margin-bottom: -16px;">
		</div>
	</form>

	<div class="introduc">
		<div class="introduc-content">
			<div class="introduc-title">
				<h3>GIỚI THIỆU</h3>
			</div>
			<div class="introduc-logo">
				<div>TẤT CẢ CÁC SẢN PHẨM ĐỀU ĐƯỢC THIẾT KẾ VÀ SẢN XUẤT BEE BOOKS</div>
				<a href="${base }"> <img src="${base }/img/logo.png" width="30%">
				</a>
			</div>
		</div>
		<div class="introduc-link">
			<div class="introduc-title">
				<h3>LIÊN KẾT</h3>
			</div>
			<div class="introduc-list">
				<ul>
					<li><a href="#">Tìm kiếm</a></li>
					<li><a href="${base }/introduction">Giới thiệu</a></li>
					<li><a href="#">Chính sách đổi trả</a></li>
					<li><a href="#">Chính sách bảo mật</a></li>
					<li><a href="#">Điều khoản dịch vụ</a></li>
				</ul>
			</div>
		</div>
		<div class="introduc-contact">
			<div class="introduc-title">
				<h3>THÔNG TIN LIÊN HỆ</h3>
			</div>
			<div class="introduc-list">
				<ul>
					<li><i class="fas fa-map-marker-alt"></i> .</li>
					<li><i class="fas fa-phone-volume"></i> 0912345678</li>
					<li><i class="fas fa-phone-volume"></i> 0123456789</li>
					<li><i class="fas fa-paper-plane"></i> bookclub2509@gmail.com
					</li>
				</ul>
			</div>
		</div>
		<div class="introduc-fanpage">
			<div class="introduc-title">
				<h3>FANPAGE</h3>
				<p>https://www.facebook.com</p>
			</div>
			<div class="introduc-list"></div>
		</div>
	</div>

</div>
<!--close footer-->
