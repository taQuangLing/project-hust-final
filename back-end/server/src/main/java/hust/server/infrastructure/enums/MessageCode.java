package hust.server.infrastructure.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum MessageCode {
    DUPLICATE (4000, "DUPLICATE", "Loi trung lap"),
    EXIST(4999, "EXIST", "Tai khoan da ton tai"),
    SUCCESS(2000, "SUCCESS", "Thành công"),
    FAIL(4001, "FAIL", "Thất bại"),
    ERROR(5000, "ERROR", "Lỗi hệ thống!"),
    ID_NOT_FOUND(4003, "ID NOT FOUND", "Không tìm thấy id"),
    EMPTY(4002, "SPECIAL RATING EMPTY", "Người dùng hệ thống chưa comment bài viết này"),
    TOKEN_ERROR(4003, "Token error", "Lỗi Token"),
    ACCOUNT_INCORRECT(4004, "Account incorrect", "Tài khoản hoặc mật khẩu không chính xác!"),
    USERNAME_ALREADY_EXISTS(4005, "Username already exists", "Tài khoản đã tồn tại"),
    BRANCH_NOT_EXIST(4006, "Branch does not exist", "Chi nhánh không tồn tại"),
    ;
    private int code;
    private String message;
    private String description;
}