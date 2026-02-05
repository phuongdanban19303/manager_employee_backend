package l3_manager_employee.Controller;


import l3_manager_employee.Config.LoginRequest;
import l3_manager_employee.Config.LoginResponse;
import l3_manager_employee.Service.Impl.AuthServiceImpl;
import l3_manager_employee.commons.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ApiResponse.success(
                authService.login(
                        request.getUsername(),
                        request.getPassword()
                )
        );
    }
}