package l3_manager_employee.DTO.Request;

import lombok.Data;

@Data
public class PromotionUpdateRequest {
    private String oldPosition;
    private String newPosition;
    private String reason;
    private Long receiverId;
    private String status;
    private String leaderNote;
}
