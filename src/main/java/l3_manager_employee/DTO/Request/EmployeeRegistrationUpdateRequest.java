package l3_manager_employee.DTO.Request;

import lombok.Data;

@Data
public class EmployeeRegistrationUpdateRequest {
    private Long id;
    private String resume;
    private String cv_url;
    private String note;
    private String job_position;
}
