package l3_manager_employee.DTO.Respones;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class ContractTerminationDetailResponse {
    private Long id;
    private Long employeeId;
    private String employeeCode;
    private String employeeName;
    private String team;
    private LocalDate  terminationDate;
    private String terminationReason;
    private Long receiverId;
    private String receiverName;
    private String status;
    private LocalDateTime submitDate;
    private LocalDate approveDate;
    private String leaderNote;
    private Long createdBy;
    private LocalDateTime createdAt;
    private Long updatedBy;
    private LocalDateTime updatedAt;
}
