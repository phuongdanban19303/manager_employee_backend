package l3_manager_employee.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // SỬA Ở ĐÂY: Dùng Pattern để cho phép tất cả các subdomain của vercel
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:*",                      // Cho phép mọi port ở localhost
                "https://*.vercel.app",                    // Cho phép mọi link có đuôi .vercel.app
                "https://manager-employee.vercel.app"      // Domain chính (để cho chắc)
        ));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));

        // Cho phép các Header quan trọng, đặc biệt là Authorization cho JWT
        configuration.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));

        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // Trình duyệt sẽ nhớ cấu hình này trong 1 giờ

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }}
