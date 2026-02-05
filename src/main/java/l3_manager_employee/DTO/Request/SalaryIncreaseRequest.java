package l3_manager_employee.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryIncreaseRequest {
    private Long employeeId;
    private Integer times;
    private String oldLevel;
    private String newLevel;
    private String reason;
    private Long receiverId;
}