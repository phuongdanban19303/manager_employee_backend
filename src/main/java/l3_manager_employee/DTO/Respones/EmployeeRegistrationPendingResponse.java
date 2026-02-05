package l3_manager_employee.DTO.Respones;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class EmployeeRegistrationPendingResponse {
    private Long formId;
    private Long employeeId;
    private String employeeCode;
    private String employeeName;
    private String jobPosition;
    private Long receiverId;
    private String status;
    private LocalDateTime submitDate;
    private LocalDateTime createdAt;
}
