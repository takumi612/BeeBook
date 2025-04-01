// Cập nhật số lượng sản phẩm trong giỏ hàng
function AddQuantityCart(baseUrl, productId) {
    let data = {
        productId: productId,

    };

    jQuery.ajax({
        url: baseUrl + "/updateQuantityCart/add",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(data),

        dataType: "json",
        success: function (jsonResult) {
            const localeVN = 'vi-VN';
            const currencyFormatter = new Intl.NumberFormat(localeVN, {
                style: 'currency',
                currency: 'VND',
                currencyDisplay: 'code'
            });
            const formattedTotalPrice = currencyFormatter.format(jsonResult.itemPrice);
            $("#quantity_" + productId).html(jsonResult.currentProductQuantity);
            $("#product_total_price_"+ productId).html(formattedTotalPrice);
            $("#total_price").html(currencyFormatter.format(jsonResult.totalPrice));
            if(jsonResult.error){
                Toastify({
                    text: jsonResult.error, // *** Sử dụng giá trị lỗi từ JSON response ***
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
            }

        },
        error: function (jqXhr, textStatus, errorMessage) {

        }
    });
}

function MinusQuantityCart(baseUrl, productId) {
    let data = {
        productId: productId,
    };
    jQuery.ajax({
        url: baseUrl + "/updateQuantityCart/minus",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(data),

        dataType: "json",
        success: function (jsonResult) {
            const localeVN = 'vi-VN';
            const currencyFormatter = new Intl.NumberFormat(localeVN, {
                style: 'currency',
                currency: 'VND',
                currencyDisplay: 'code'
            });
            $("#quantity_" + productId).html(jsonResult.currentProductQuantity);
            $("#product_total_price_"+ productId).html(currencyFormatter.format(jsonResult.itemPrice));
            $("#total_price").html(currencyFormatter.format(jsonResult.totalPrice));
        },
        error: function (jqXhr, textStatus, errorMessage) {
        }
    });
}


// Thêm sản phẩm vào trong giỏ hàng
function AddToCart(baseUrl, productId, quantity) {
    let data = {
        productId: productId,
        quantity: quantity,
    };

    jQuery.ajax({
        url: baseUrl + "/addToCart",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: "json",
        success: function (jsonResult) {
            if (jsonResult.status === "out_of_stock") {
                alert("Sản phẩm này đã hết hàng.");
            } else {
                $("#iconShowTotalItemsInCart").html(jsonResult.totalItems);
                $([document.documentElement, document.body]).animate({
                    scrollTop: $("#iconShowTotalItemsInCart").offset().top
                }, 2000);
            }
        },
        error: function (jqXhr, textStatus, errorMessage) {

        }
    });
}


//dành cho subscribe
home = function (baseUrl) {
    let data = {
        email: $("#email").val(),
    };

    if (validateEmail(data.email)) {
        $.ajax({
            url: baseUrl + "/subscribe",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(data),

            dataType: "json",
            success: function (jsonResult) {
                $("#email").val("");
                $("#TB_AJAX").html(jsonResult.message);
            },
            error: function (errMessage) {
                $("#TB_AJAX").html(errMessage.message);
            }
        });
    } else {
        $("#TB_AJAX").html("Địa chỉ email không đúng định dạng");
    }

}

//dành cho contact
contact = function (baseUrl) {
    let data = {
        email: $("#email").val(),
        name: $("#name").val(),
        massage: $("#massage").val(),
    };

    var flag = true;
    //email
    if (validateEmail(data.email)) {
        $.ajax({
            url: baseUrl + "/sendContact",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (jsonResultCt) {
                $("#TB_AJAX_CONTACT").html(jsonResultCt.messages);
            },
            error: function (jsonResultCt) {
                $("#TB_AJAX_CONTACT").html(jsonResultCt.message);
            }
        });
    } else {
        $("#TB_AJAX_CONTACT").html("Địa chỉ email không đúng định dạng");
    }


}

//dành cho register
register = function (baseUrl) {
    let dataRegister = {
        email: $("#email").val(),
        username: $("#username").val(),
    };
    if (validateEmail(dataRegister.email)) {
        $.ajax({
            url: baseUrl + "/register",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(dataRegister),
            dataType: "json",
            success: function (jsonResultCt) {
                $("#email").val("");
                $("#TB_REGISTER").html(jsonResultCt.messages);
            },
            error: function (jsonResultCt) {
            }
        });
    } else {
    }
}

function validateEmail(email) {
    const mailFormat = /^[A-Za-z0-9]{6,32}@([a-zA-Z0-9]{2,12})(.[a-zA-Z]{2,12})+$/;
    return mailFormat.test(String(email).toLowerCase());

}



