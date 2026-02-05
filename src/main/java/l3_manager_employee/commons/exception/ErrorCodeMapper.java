package l3_manager_employee.commons.exception;

public class ErrorCodeMapper {

    public static AppException map(String code) {

        if (code == null || code.isBlank()) {
            return new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return switch (code) {

            /* ===== SUCCESS ===== */
            case "SUCCESS" -> new AppException(ErrorCode.SUCCESS);

            /* ===== COMMON ===== */
            case "INVALID_INPUT" -> new AppException(ErrorCode.INVALID_INPUT);
            case "INVALID_JSON_FORMAT" -> new AppException(ErrorCode.INVALID_JSON_FORMAT);
            case "SYSTEM_ERROR" -> new AppException(ErrorCode.SYSTEM_ERROR);
            case "SYSTEM_ERROR_DB" -> new AppException(ErrorCode.SYSTEM_ERROR_DB);

            /* ===== PERMISSION ===== */
            case "PERMISSION_DENIED" -> new AppException(ErrorCode.PERMISSION_DENIED);
            case "CREATE_FORBIDDEN" -> new AppException(ErrorCode.FORM_CREATE_FORBIDDEN);
            case "SUBMIT_FORBIDDEN" -> new AppException(ErrorCode.FORM_SUBMIT_FORBIDDEN);
            case "MANAGER_ONLY" -> new AppException(ErrorCode.MANAGER_ONLY);

            /* ===== USER ===== */
            case "USER_NOT_FOUND" -> new AppException(ErrorCode.USER_NOT_FOUND);

            /* ===== EMPLOYEE ===== */
            case "EMP_NOT_FOUND", "EMPLOYEE_NOT_FOUND"
                    -> new AppException(ErrorCode.EMP_NOT_FOUND);

            case "EMP_INVALID_STATUS", "EMPLOYEE_NOT_APPROVED"
                    -> new AppException(ErrorCode.EMP_INVALID_STATUS);

            case "EMP_NOT_CREATOR", "EMPLOYEE_NOT_OWNER"
                    -> new AppException(ErrorCode.EMP_NOT_CREATOR);

            /* ===== FORM ===== */
            case "FORM_NOT_FOUND" -> new AppException(ErrorCode.FORM_NOT_FOUND);
            case "FORM_NOT_PENDING" -> new AppException(ErrorCode.FORM_NOT_PENDING);

            case "FORM_INVALID_STATUS", "INVALID_STATUS", "FORM_NOT_EDITABLE"
                    -> new AppException(ErrorCode.FORM_INVALID_STATUS);

            case "FORM_CREATE_FORBIDDEN"
                    -> new AppException(ErrorCode.FORM_CREATE_FORBIDDEN);

            case "FORM_SUBMIT_FORBIDDEN"
                    -> new AppException(ErrorCode.FORM_SUBMIT_FORBIDDEN);

            /* ===== REQUIRED DATA ===== */
            case "EMP_REQUIRED" -> new AppException(ErrorCode.EMP_REQUIRED);
            case "CERTIFICATE_REQUIRED" -> new AppException(ErrorCode.CERTIFICATE_REQUIRED);
            case "FAMILY_REQUIRED", "FAMILY_RELATION_REQUIRED"
                    -> new AppException(ErrorCode.FAMILY_REQUIRED);

            /* ===== ACTION ===== */
            case "INVALID_ACTION" -> new AppException(ErrorCode.INVALID_ACTION);
            case "RECEIVER_REQUIRED" -> new AppException(ErrorCode.RECEIVER_REQUIRED);
            case "FORM_NOT_WAIT_APPROV" -> new AppException(ErrorCode.FORM_NOT_WAIT_APPROV);

            /* ===== FALLBACK ===== */
            default -> new AppException(ErrorCode.UNKNOWN_ERROR);
        };
    }
}
