package l3_manager_employee.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import l3_manager_employee.commons.exception.ErrorCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int code;
    private final String message;
    private final T data;

    private ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /* ===== SUCCESS ===== */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                ErrorCode.SUCCESS.getCode(),
                ErrorCode.SUCCESS.getMessage(),
                data
        );
    }

    /* ===== ERROR ===== */
    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(
                errorCode.getCode(),
                errorCode.getMessage(),
                null
        );
    }
}