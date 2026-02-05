package l3_manager_employee.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProposalRequest {
    private Long employeeId;
    private String content;
    private String detail;
    private Long receiverId;
}