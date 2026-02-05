package l3_manager_employee.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeRequest {

    private String fullName;

    private String gender;
    @NotNull(message = "Ngày sinh không được để trống")
    private LocalDate dateOfBirth;

    private String address;

    private String team;

    private String avatarUrl;

    private String identityNumber;

    private String phone;

    private String email;
}
