package l3_manager_employee.Config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static UserPrincipal getCurrentUser() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return (UserPrincipal) auth.getPrincipal();
    }

    public static Long getUserId() {
        return getCurrentUser().getUserId();
    }

    public static String getRole() {
        return getCurrentUser().getRole();
    }
}
