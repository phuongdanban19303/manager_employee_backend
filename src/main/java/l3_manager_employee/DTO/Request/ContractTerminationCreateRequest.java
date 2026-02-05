package l3_manager_employee.DTO.Request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ContractTerminationCreateRequest {
    private Long employeeId;
    private LocalDate terminationDate;
    private String terminationReason;
}