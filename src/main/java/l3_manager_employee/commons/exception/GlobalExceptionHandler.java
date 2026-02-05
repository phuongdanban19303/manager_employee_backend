package l3_manager_employee.commons.exception;

import jakarta.validation.ConstraintViolationException;
import l3_manager_employee.commons.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /* ===== BUSINESS ERROR ===== */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(AppException ex) {
        log.warn("Business error: {}", ex.getErrorCode());
        return ResponseEntity.ok(ApiResponse.error(ex.getErrorCode()));
    }

    /* ===== VALIDATION ERROR ===== */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {

        log.warn("Validation error", ex);
        return ResponseEntity.ok(ApiResponse.error(ErrorCode.INVALID_INPUT));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(
            ConstraintViolationException ex) {

        log.warn("Validation error", ex);
        return ResponseEntity.ok(ApiResponse.error(ErrorCode.INVALID_INPUT));
    }


    /* ===== UNKNOWN ERROR ===== */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error("System error", ex);
        return ResponseEntity.ok(ApiResponse.error(ErrorCode.SYSTEM_ERROR));
    }
}
