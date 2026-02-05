package l3_manager_employee.DTO.Respones;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class FormSalaryIncreaseResponse {
    private Long id;
    private Long employeeId;
    private Integer times;
    private String oldLevel;
    private String newLevel;
    private String reason;
    private Long receiverId;
    private String status;
    private LocalDateTime submitDate;
    private LocalDateTime approveDate;
    private String leaderNote;
    private LocalDateTime createdAt;
}
