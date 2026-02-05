package l3_manager_employee.DTO.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProcessEmployeeRegistrationRequest {

    private Long formId;

    private String action;

    private String note;
    private LocalDate actionDate;
}
