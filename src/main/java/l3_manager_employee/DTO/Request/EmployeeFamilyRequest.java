package l3_manager_employee.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeFamilyRequest {
    private String full_name;
    private Long gender;
    private LocalDate date_of_birth;
    private String identity_card_number;
    private String relationship;
    private String address;
}
