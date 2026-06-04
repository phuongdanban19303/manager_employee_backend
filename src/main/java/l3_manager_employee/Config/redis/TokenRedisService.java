package l3_manager_employee.Config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenRedisService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String REFRESH_TOKEN_PREFIX = "auth:refresh:";
    private static final String BLACKLIST_PREFIX = "auth:blacklist:";

    public void saveRefreshToken(Integer userId, String refreshToken, long ttlMillis) {
        String key = REFRESH_TOKEN_PREFIX + userId;

        redisTemplate.opsForValue().set(
                key,
                refreshToken,
                Duration.ofMillis(ttlMillis)
        );
    }

    public boolean isRefreshTokenValid(Integer userId, String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        String tokenInRedis = redisTemplate.opsForValue().get(key);

        return refreshToken.equals(tokenInRedis);
    }

    public void deleteRefreshToken(Integer userId) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        redisTemplate.delete(key);
    }

    public void blacklistAccessToken(String accessToken, long ttlMillis) {
        if (ttlMillis <= 0) {
            return;
        }

        String key = BLACKLIST_PREFIX + accessToken;

        redisTemplate.opsForValue().set(
                key,
                "BLACKLISTED",
                Duration.ofMillis(ttlMillis)
        );
    }

    public boolean isAccessTokenBlacklisted(String accessToken) {
        String key = BLACKLIST_PREFIX + accessToken;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}