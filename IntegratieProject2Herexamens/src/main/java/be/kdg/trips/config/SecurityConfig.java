package be.kdg.trips.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableTransactionManagement
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/index").access("permitAll")
				.and()
				.formLogin().loginPage("/login").permitAll().failureUrl("/login?error")
				.usernameParameter("username")
				.passwordParameter("password")
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll()
				.and().csrf()
				.and().exceptionHandling().accessDeniedPage("/403")
				.and()
				.logout().logoutSuccessUrl("/login?logout").permitAll();
	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder(){
//		PasswordEncoder encoder = new BCryptPasswordEncoder();
//		return encoder;
//	}
	
}