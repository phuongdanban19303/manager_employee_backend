package l3_manager_employee.DTO.Respones;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EmployeeListRegistrationResponse {
    private Long id;

    private Long employee_id;

    private String resume;

    private String cv_url;

    private String note;

    private String job_position;

    private Long receiver_id    ;

    private String status;

    private LocalDateTime submit_date;

    private LocalDateTime approve_date;

    private String leader_note;

    private Long created_by;

    private LocalDateTime created_at;

    private Long updated_by;

    private LocalDateTime updated_at;
}
