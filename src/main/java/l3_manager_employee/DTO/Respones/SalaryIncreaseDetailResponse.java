package l3_manager_employee.DTO.Respones;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SalaryIncreaseDetailResponse {

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
    private Long createdBy;
    private LocalDateTime createdAt;
    private Long updatedBy;
    private LocalDateTime updatedAt;
}