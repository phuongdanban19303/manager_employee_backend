package l3_manager_employee.DTO.Request;

import lombok.Data;

@Data
public class SubmitContractTerminationRequest {
    private Long formId;
    private Long receiverId; // leaderId tiếp nhận
}