package l3_manager_employee.DTO.Request;

import lombok.Data;

@Data
public class SalaryIncreaseUpdateRequest {
    private Integer times;
    private String oldLevel;
    private String newLevel;
    private String reason;
    private Long receiverId;
    private String status;
    private String leaderNote;
}
