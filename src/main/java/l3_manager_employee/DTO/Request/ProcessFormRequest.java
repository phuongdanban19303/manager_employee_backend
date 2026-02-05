package l3_manager_employee.DTO.Request;

import lombok.Data;

@Data
public class ProcessFormRequest {
    private Long formId;
    private String action; // APPROVED | UPDATE | REJECTED

    private String leaderNote;
}
