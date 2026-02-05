package l3_manager_employee.DTO.Respones;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SalaryIncreasePendingResponse {
    private Long formId;
    private Long employeeId;
    private String employeeCode;
    private String employeeName;
    private Integer times;
    private String oldLevel;
    private String newLevel;
    private Long receiverId;
    private String status;
    private LocalDateTime submitDate;
    private LocalDateTime createdAt;
}