<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!-- Reject Order Modal -->
<div class="modal fade" id="rejectOrderModal" tabindex="-1" role="dialog" aria-labelledby="rejectOrderModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="rejectOrderModalLabel">Xác nhận từ chối đơn hàng</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">
                Bạn có chắc chắn muốn từ chối đơn hàng này không?
                <p class="text-danger">Hành động này không thể hoàn tác!</p>
            </div>
            <form class="needs-validation"
                     novalidate="" action="${base }/admin/orderReject"
                     method="post" enctype="multipart/form-data">

                <input type="hidden" id="rejectOrderCodeInput" name="orderCode" value=""/>
                <input type="hidden" id="returnUrlInput" name="returnUrl" value="" />


                <div class="form-row">
                    <div class="col-md-6 mb-3">
                        <label for="validationCustom03"></label>
                        <textarea autocomplete="off" type="text"
                                     class="form-control" id="validationCustom03"
                                     placeholder="Lý do hủy đơn ..." name="reason" required="required" rows="3"></textarea>
                        <div class="invalid-feedback">Hãy nhập lý do!</div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy bỏ</button>
                    <button type="submit" class="btn btn-secondary">Lưu</button>
                </div>
            </form>

        </div>
    </div>
</div>