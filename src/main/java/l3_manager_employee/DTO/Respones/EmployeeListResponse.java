package l3_manager_employee.DTO.Respones;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeListResponse {

    private Long id;
    private String employeeCode;
    private String fullName;
    private String team;
    private String status;
}