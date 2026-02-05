package l3_manager_employee.DTO.Respones;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class EmployeeFamilyRelationResponse {
    private Integer id;
    private Integer employeeId;
    private String full_name;
    private Integer gender;
    private LocalDate date_of_birth;
    private String identity_card_number;
    private String relationship;
    private String address;
    private Long createdBy;
    private LocalDateTime createdAt;
    private Long updatedBy;
    private LocalDateTime updatedAt;
}
