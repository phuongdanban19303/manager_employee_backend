package l3_manager_employee.Service.Impl;

import l3_manager_employee.Config.JwtUtil;
import l3_manager_employee.Config.LoginResponse;
import l3_manager_employee.Config.PasswordConfig;
import l3_manager_employee.Enity.TblUser;
import l3_manager_employee.Repository.TblUserRepository;
import l3_manager_employee.commons.exception.AppException;
import l3_manager_employee.commons.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl {

    private final JwtUtil jwtUtil;
    private final TblUserRepository userRepo;
    private final PasswordEncoder passwordEncoder; // ✅ ĐÚNG

    public LoginResponse login(String username, String password) {

        TblUser user = userRepo.findByUsernameAndStatus(username, "ACTIVE")
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_INPUT));

        boolean match = passwordEncoder.matches(password, user.getPassword());

        if (!match) {
            throw new AppException(ErrorCode.INVALID_INPUT);
        }
        String token = jwtUtil.generateToken(user);

        return new LoginResponse(token);
    }
}