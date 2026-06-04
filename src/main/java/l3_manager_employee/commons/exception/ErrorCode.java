package l3_manager_employee.commons.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* ===== SUCCESS ===== */
    SUCCESS(200000, "Success"),

    /* ===== COMMON ===== */
    INVALID_INPUT(400000, "Invalid input"),
    INVALID_JSON_FORMAT(400001, "Invalid JSON format"),

    PERMISSION_DENIED(403000, "Permission denied"),
    SYSTEM_ERROR(500000, "System error"),
    SYSTEM_ERROR_DB(500001, "Database error"),

    /* ===== AUTH ===== */
    USERNAME_REQUIRED(400100, "Username is required"),
    PASSWORD_REQUIRED(400101, "Password is required"),
    USERNAME_ALREADY_EXISTS(400102, "Username already exists"),
    INVALID_USERNAME_OR_PASSWORD(401100, "Invalid username or password"),
    ACCOUNT_DISABLED(403100, "Account is disabled"),
    TOKEN_MISSING(401101, "Token is missing"),
    TOKEN_INVALID(401102, "Token is invalid"),
    TOKEN_EXPIRED(401103, "Token expired"),
    REFRESH_TOKEN_INVALID(401104, "Refresh token invalid"),
    REFRESH_TOKEN_EXPIRED(401105, "Refresh token expired"),
    LOGOUT_FAILED(400103, "Logout failed"),

    /* ===== USER ===== */
    USER_NOT_FOUND(404000, "User not found"),

    /* ===== EMPLOYEE ===== */
    EMP_NOT_FOUND(404001, "Employee not found"),
    EMP_INVALID_STATUS(400004, "Employee status invalid"),
    EMP_NOT_CREATOR(403006, "Employee not creator"),

    /* ===== FORM – COMMON ===== */
    FORM_NOT_FOUND(404010, "Form not found"),
    FORM_INVALID_STATUS(400010, "Form invalid status"),
    FORM_NOT_PENDING(400011, "Form not pending"),
    FORM_CREATE_FORBIDDEN(403011, "Create form forbidden"),
    FORM_SUBMIT_FORBIDDEN(403012, "Submit form forbidden"),

    /* ===== FORM – REQUIRED DATA ===== */
    EMP_REQUIRED(400020, "Employee information required"),
    CERTIFICATE_REQUIRED(400021, "Certificate required"),
    FAMILY_REQUIRED(400022, "Family information required"),

    /* ===== ACTION / APPROVAL ===== */
    INVALID_ACTION(400030, "Invalid action"),
    RECEIVER_REQUIRED(400040, "Receiver is required"),
    MANAGER_ONLY(403040, "Only manager can approve"),

    /* ===== UNKNOWN ===== */
    UNKNOWN_ERROR(500999, "Unknown error"),
    FORM_NOT_WAIT_APPROV(400050, "Form not wait approve");

    private final int code;
    private final String message;
}