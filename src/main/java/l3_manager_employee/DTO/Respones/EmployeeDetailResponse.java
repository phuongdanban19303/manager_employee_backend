package l3_manager_employee.DTO.Respones;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class EmployeeDetailResponse {

    private Long id;
    private String employeeCode;
    private String fullName;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;
    private String team;
    private String avatarUrl;
    private String identityNumber;
    private String phone;
    private String email;
    private String status;
    private LocalDateTime statusUpdatedAt;
    private LocalDate terminatedAt;
    private String terminationReason;
    private LocalDate archiveDate;
    private String archiveNumber;
    private Long createdBy;
    private LocalDateTime createdAt;
    private Long updatedBy;
    private LocalDateTime updatedAt;
}
