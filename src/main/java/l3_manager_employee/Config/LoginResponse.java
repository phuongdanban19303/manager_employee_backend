package l3_manager_employee.Config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class LoginResponse {
    private String token;
    private String tokenType;
    public LoginResponse(String token) {
        this.token = token;
        this.tokenType = "Bearer";
    }
}
