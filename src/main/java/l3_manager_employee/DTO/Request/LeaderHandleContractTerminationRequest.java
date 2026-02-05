package l3_manager_employee.DTO.Request;

import lombok.Data;

@Data
public class LeaderHandleContractTerminationRequest {
    private Long formId;
    private String action; // APPROVE | REQUEST_MORE | REJECT
    private String note;
}