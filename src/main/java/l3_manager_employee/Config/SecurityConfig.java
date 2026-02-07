package l3_manager_employee.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Cấu hình CORS: Dùng cái bean corsConfigurationSource ở dưới
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. Tắt CSRF (Bắt buộc khi dùng JWT/Stateless)
                .csrf(AbstractHttpConfigurer::disable)

                // 3. Session Stateless: Server không lưu trạng thái user
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. Phân quyền
                .authorizeHttpRequests(auth -> auth
                        // Cho phép method OPTIONS (để trình duyệt check CORS trước khi gửi request thật)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Cho phép truy cập tự do vào các API login/register
                        .requestMatchers("/auth/**").permitAll()

                        // Tất cả request còn lại phải có Token
                        .anyRequest().authenticated()
                )

                // 5. Thêm Filter JWT trước filter gốc của Spring
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // === QUAN TRỌNG: Hàm này phải nằm TRONG class SecurityConfig ===
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Cho phép tất cả các domain gọi vào (Dùng '*' để test cho dễ, sau này sửa thành domain frontend cụ thể)
        configuration.setAllowedOrigins(List.of("*"));

        // Cho phép các method HTTP
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));

        // Cho phép mọi header (Authorization, Content-Type...)
        configuration.setAllowedHeaders(List.of("*"));

        // Đăng ký cấu hình này cho mọi đường dẫn
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}