package l3_manager_employee.commons.exception;

import jakarta.validation.ConstraintViolationException;
import l3_manager_employee.commons.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(AppException ex) {
        log.warn("Business error: {}", ex.getErrorCode());

        HttpStatus status = toHttpStatus(ex.getErrorCode());

        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(ex.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex
    ) {
        log.warn("Validation error", ex);

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ErrorCode.INVALID_INPUT));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(
            ConstraintViolationException ex
    ) {
        log.warn("Validation error", ex);

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ErrorCode.INVALID_INPUT));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(
            BadCredentialsException ex
    ) {
        log.warn("Bad credentials", ex);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(ErrorCode.INVALID_USERNAME_OR_PASSWORD));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error("System error", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(ErrorCode.SYSTEM_ERROR));
    }

    private HttpStatus toHttpStatus(ErrorCode errorCode) {
        int code = errorCode.getCode();

        if (code >= 400000 && code < 401000) {
            return HttpStatus.BAD_REQUEST;
        }

        if (code >= 401000 && code < 402000) {
            return HttpStatus.UNAUTHORIZED;
        }

        if (code >= 403000 && code < 404000) {
            return HttpStatus.FORBIDDEN;
        }

        if (code >= 404000 && code < 405000) {
            return HttpStatus.NOT_FOUND;
        }

        if (code >= 500000) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.OK;
    }
}