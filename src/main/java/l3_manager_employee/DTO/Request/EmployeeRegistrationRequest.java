package l3_manager_employee.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRegistrationRequest {
    private String resume;
    private String cv_url;
    private String note;
    private String job_position;
    private Long receiverId;
}
