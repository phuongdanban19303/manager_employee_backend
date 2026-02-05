package l3_manager_employee.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeUpdateRequest {
    private String fullName;
    private String gender;
    private String dateOfBirth;
    private String address;
    private String team;
    private String avatarUrl;
    private String identityNumber;
    private String phone;
    private String email;
}
