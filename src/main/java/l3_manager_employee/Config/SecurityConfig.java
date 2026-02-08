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
                // 1. Cấu hình CORS (Sử dụng Bean corsConfigurationSource bên dưới)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. Tắt CSRF (Bắt buộc với JWT)
                .csrf(AbstractHttpConfigurer::disable)

                // 3. Stateless Session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. Phân quyền
                .authorizeHttpRequests(auth -> auth
                        // Cho phép method OPTIONS (để trình duyệt không chặn)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Cho phép API Login/Register truy cập tự do
                        .requestMatchers("/auth/**").permitAll()

                        // Các API khác bắt buộc phải có Token
                        .anyRequest().authenticated()
                )

                // 5. Thêm Filter JWT
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Bean cấu hình CORS (Thay thế cho file CorsConfig.java cũ)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // CẤU HÌNH CHO PHÉP FRONTEND TRUY CẬP
        // Khi deploy thật, nên thay "*" bằng domain frontend cụ thể (VD: https://web-cua-ban.vercel.app)
        configuration.setAllowedOrigins(List.of("*"));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(List.of("*"));
        // configuration.setAllowCredentials(true); // Lưu ý: Nếu để "*" ở Origins thì phải comment dòng này lại, hoặc phải chỉ định rõ domain cụ thể.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}