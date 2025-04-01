package beebooks.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
public class JpaAuditingConfiguration {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            if(securityContext == null){
                return Optional.of("Guest");
            }

            Authentication authentication = securityContext.getAuthentication();
            if(authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")){
                return Optional.of("Guest");
            }
            String username = authentication.getName();
            return Optional.of(username);
        };
    }
}
