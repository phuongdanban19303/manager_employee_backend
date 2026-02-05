package l3_manager_employee.DTO.Respones;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractTerminationResponse {
    private Long formId;
    private Long employeeId;
    private String employeeCode;
    private String fullName;
    private LocalDate terminationDate;
    private String status;
    private LocalDate submitDate;
    private String archiveNumber;
}