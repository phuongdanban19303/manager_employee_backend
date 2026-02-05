package l3_manager_employee.DTO.Respones;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class EmployeeCertificateDetailResponse {

    private Long id;

    private Long employeeId;

    private String name;

    private LocalDate issue_date;

    private String content;

    private String field_url;

    private Long createdBy;

    private LocalDateTime createdAt;

    private Long updatedBy;

    private LocalDateTime updatedAt;
}