package l3_manager_employee.DTO.Respones;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class ContractTerminationPendingResponse {

    private Long formId;
    private Long employeeId;
    private String employeeCode;
    private String employeeName;
    private LocalDate terminationDate;
    private Long receiverId;
    private String receiverName;
    private String status;
    private LocalDateTime submitDate;

    // getter / setter
}
