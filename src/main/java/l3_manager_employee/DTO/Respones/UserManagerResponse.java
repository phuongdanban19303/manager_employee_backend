package l3_manager_employee.DTO.Respones;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
@Builder
public class UserManagerResponse {

    private Long id;
    private String username;
    private String fullname;
    private String role;
    private String status;
    private LocalDateTime createdAt;
    private String team;
}
