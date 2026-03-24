package phattrienungdung2ee.webbanhang.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterForm {

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 3, max = 50, message = "Tên đăng nhập từ 3 đến 50 ký tự")
    private String loginName;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 100, message = "Mật khẩu từ 6 đến 100 ký tự")
    private String password;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    private String confirmPassword;

    @AssertTrue(message = "Mật khẩu xác nhận không khớp")
    public boolean isPasswordMatch() {
        if (password == null || confirmPassword == null) {
            return true;
        }
        return password.equals(confirmPassword);
    }
}
