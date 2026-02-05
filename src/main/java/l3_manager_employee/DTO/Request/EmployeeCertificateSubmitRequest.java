package l3_manager_employee.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeCertificateSubmitRequest {
    private String note;
    private Long receiverId;
}
