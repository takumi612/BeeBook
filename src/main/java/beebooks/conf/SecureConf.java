package beebooks.conf;

import beebooks.securityFilter.UserStatusCheckFilter;
import beebooks.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecureConf extends WebSecurityConfigurerAdapter {

	private final UserDetailsServiceImpl userDetailsService;
	private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/upload/**", "/login", "/logout","/home").permitAll()
				.antMatchers("/admin/**").hasAuthority("ADMIN")
				.antMatchers("/user/**","/customer/**").hasAuthority("USER")
				.and()
				.formLogin().loginPage("/login").loginProcessingUrl("/perform_login")
				.successHandler(authenticationSuccessHandler())
				.failureUrl("/login?login_error=true")
				.and()
				.logout().logoutUrl("/logout")
				.logoutSuccessHandler(customLogoutSuccessHandler)
				.invalidateHttpSession(true)
				.deleteCookies();

		http.addFilterBefore(userStatusCheckFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	private UserStatusCheckFilter userStatusCheckFilter() {
		return new UserStatusCheckFilter(userDetailsService);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder(4));
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new UrlAuthenticationSuccessHandler();
	}
}