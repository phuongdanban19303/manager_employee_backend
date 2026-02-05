package l3_manager_employee.DTO.Respones;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeApprovedDTO {
    private Long id;
    private String employeeCode;
    private String fullName;
    private String team;
    private String status;
}
