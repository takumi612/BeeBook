package beebooks.securityFilter;

import beebooks.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserStatusCheckFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;


    public UserStatusCheckFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!isLoginRequest(httpServletRequest)) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String username = httpServletRequest.getParameter("username");
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if(userDetails instanceof User) {
                User user = (User) userDetails;
                if(!user.getStatus()){
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login?login_error=true&message=account_disabled");
                    return;
                }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean isLoginRequest(HttpServletRequest request) {
        return request.getMethod().equals("POST") &&
                request.getRequestURI().endsWith("/perform_login");
    }
}
