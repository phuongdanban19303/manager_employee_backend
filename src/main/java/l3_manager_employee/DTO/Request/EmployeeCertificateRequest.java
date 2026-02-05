package l3_manager_employee.DTO.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeCertificateRequest {
    private String name;
    private LocalDate issue_date;
    private String content;
    private String field_url;
}
