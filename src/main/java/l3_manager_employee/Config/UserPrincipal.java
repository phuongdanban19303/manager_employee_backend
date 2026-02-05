package l3_manager_employee.Config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class UserPrincipal {
    private Long userId;
    private String role;
}
