package l3_manager_employee.DTO.Request;

import lombok.Data;

@Data
public class SubmitFormRequest {
    private Long formId;
    private Long receiverId;
}
