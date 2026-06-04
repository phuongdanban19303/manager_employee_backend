package l3_manager_employee.Service.Impl;

import l3_manager_employee.Config.JwtUtil;
import l3_manager_employee.Config.LoginResponse;
import l3_manager_employee.Config.RegisterRequest;
import l3_manager_employee.Config.redis.RefreshTokenRequest;
import l3_manager_employee.Config.redis.TokenRedisService;
import l3_manager_employee.Enity.TblUser;
import l3_manager_employee.Repository.TblUserRepository;
import l3_manager_employee.commons.exception.AppException;
import l3_manager_employee.commons.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl {

    private final JwtUtil jwtUtil;
    private final TblUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenRedisService tokenRedisService;

    public LoginResponse register(RegisterRequest request) {

        if (!StringUtils.hasText(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_REQUIRED);
        }

        if (!StringUtils.hasText(request.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_REQUIRED);
        }

        if (userRepo.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        TblUser user = new TblUser();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus("ACTIVE");

        if (StringUtils.hasText(request.getRole())) {
            user.setRole(request.getRole());
        } else {
            user.setRole("USER");
        }

        if (StringUtils.hasText(request.getTeam())) {
            user.setTeam(request.getTeam());
        }

        TblUser savedUser = userRepo.save(user);

        String accessToken = jwtUtil.generateAccessToken(savedUser);
        String refreshToken = jwtUtil.generateRefreshToken(savedUser);

        long refreshTtl = jwtUtil.getRemainingTime(refreshToken);
        tokenRedisService.saveRefreshToken(savedUser.getId(), refreshToken, refreshTtl);

        return new LoginResponse(accessToken, refreshToken);
    }

    public LoginResponse login(String username, String password) {

        if (!StringUtils.hasText(username)) {
            throw new AppException(ErrorCode.USERNAME_REQUIRED);
        }

        if (!StringUtils.hasText(password)) {
            throw new AppException(ErrorCode.PASSWORD_REQUIRED);
        }

        TblUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_USERNAME_OR_PASSWORD));

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new AppException(ErrorCode.ACCOUNT_DISABLED);
        }

        boolean match = passwordEncoder.matches(password, user.getPassword());

        if (!match) {
            throw new AppException(ErrorCode.INVALID_USERNAME_OR_PASSWORD);
        }

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        long refreshTtl = jwtUtil.getRemainingTime(refreshToken);
        tokenRedisService.saveRefreshToken(user.getId(), refreshToken, refreshTtl);

        return new LoginResponse(accessToken, refreshToken);
    }

    public LoginResponse refreshToken(RefreshTokenRequest request) {

        String refreshToken = request.getRefreshToken();

        if (!StringUtils.hasText(refreshToken)) {
            throw new AppException(ErrorCode.TOKEN_MISSING);
        }

        if (!jwtUtil.isValidToken(refreshToken)) {
            throw new AppException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        if (!jwtUtil.isRefreshToken(refreshToken)) {
            throw new AppException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        Integer userId = jwtUtil.getUserId(refreshToken);

        boolean validInRedis = tokenRedisService.isRefreshTokenValid(userId, refreshToken);

        if (!validInRedis) {
            throw new AppException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        TblUser user = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new AppException(ErrorCode.ACCOUNT_DISABLED);
        }

        String newAccessToken = jwtUtil.generateAccessToken(user);

        return new LoginResponse(newAccessToken, refreshToken);
    }

    public void logout(String accessToken, String refreshToken) {

        if (StringUtils.hasText(accessToken)) {
            long accessTtl = jwtUtil.getRemainingTime(accessToken);
            tokenRedisService.blacklistAccessToken(accessToken, accessTtl);
        }

        if (StringUtils.hasText(refreshToken) && jwtUtil.isValidToken(refreshToken)) {
            Integer userId = jwtUtil.getUserId(refreshToken);
            tokenRedisService.deleteRefreshToken(userId);
        }
    }
}