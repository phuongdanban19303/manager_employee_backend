package l3_manager_employee.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionRequest {
    private Long employeeId;
    private String oldPosition;
    private String newPosition;
    private String reason;
    private Long receiverId;
}
