package beebooks.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CustomerDto {

    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 8, message = "Họ và tên cần tối thiểu 8 ký tự")
    private String customerName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Pattern(regexp = "^[A-Za-z0-9]{6,32}@([a-zA-Z0-9]{2,12})(.[a-zA-Z]{2,12})+$",
            message = "Email không hợp lệ")
    private String customerEmail;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10}$", message = "Nhập đúng định dạng số điện thoại(10 số)")
    private String customerPhone;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(min = 15, message = "Địa chỉ cần tối thiểu 15 ký tự")
    private String customerAddress;

    private String code;

    // Add note field from the form
    private String note;
}