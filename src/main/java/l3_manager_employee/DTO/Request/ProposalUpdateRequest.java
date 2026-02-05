package l3_manager_employee.DTO.Request;

import lombok.Builder;
import lombok.Data;

@Data
public class ProposalUpdateRequest {
    private String content;
    private String detail;
    private Long receiverId;
    private String status;
    private String leaderNote;
}
