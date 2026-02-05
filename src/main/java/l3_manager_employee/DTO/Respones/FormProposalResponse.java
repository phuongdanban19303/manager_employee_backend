package l3_manager_employee.DTO.Respones;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class FormProposalResponse {
    private Long id;
    private Long employeeId;
    private String content;
    private String detail;
    private Long receiverId;
    private String status;
    private LocalDateTime submitDate;
    private LocalDateTime approveDate;
    private String leaderNote;
    private LocalDateTime createdAt;
}
