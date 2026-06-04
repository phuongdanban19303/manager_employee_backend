package l3_manager_employee.Controller;

import jakarta.validation.Valid;
import l3_manager_employee.Config.LoginRequest;
import l3_manager_employee.Config.LoginResponse;
import l3_manager_employee.Config.RegisterRequest;
import l3_manager_employee.Config.redis.RefreshTokenRequest;
import l3_manager_employee.Service.Impl.AuthServiceImpl;
import l3_manager_employee.commons.ApiResponse;
import l3_manager_employee.commons.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ApiResponse<LoginResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        LoginResponse response = authService.register(request);
        return ApiResponse.success(response);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        LoginResponse response = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        return ApiResponse.success(response);
    }

    @PostMapping("/refresh-token")
    public ApiResponse<LoginResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        LoginResponse response = authService.refreshToken(request);
        return ApiResponse.success(response);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody(required = false) RefreshTokenRequest request
    ) {
        String accessToken = null;

        if (authorization != null && authorization.startsWith("Bearer ")) {
            accessToken = authorization.substring(7);
        }

        String refreshToken = request != null ? request.getRefreshToken() : null;

        authService.logout(accessToken, refreshToken);

        return ApiResponse.success(null);
    }
}