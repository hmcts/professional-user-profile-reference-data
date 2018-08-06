package uk.gov.hmcts.reform.ref.pup.config;

import uk.gov.hmcts.reform.auth.checker.core.RequestAuthorizer;
import uk.gov.hmcts.reform.auth.checker.core.service.Service;
import uk.gov.hmcts.reform.auth.checker.core.user.User;
import uk.gov.hmcts.reform.auth.checker.spring.serviceanduser.AuthCheckerServiceAndUserFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthCheckerServiceAndUserFilter serviceAndUserFilter;

    @Autowired
    public SecurityConfiguration(final RequestAuthorizer<User> userRequestAuthorizer,
                                 final RequestAuthorizer<Service> serviceRequestAuthorizer,
                                 final AuthenticationManager authenticationManager) {

        this.serviceAndUserFilter = new AuthCheckerServiceAndUserFilter(serviceRequestAuthorizer, userRequestAuthorizer);
        this.serviceAndUserFilter.setAuthenticationManager(authenticationManager);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilter(serviceAndUserFilter)
            .sessionManagement().sessionCreationPolicy(STATELESS).and()
            .csrf().disable()
            .formLogin().disable()
            .logout().disable()
            .authorizeRequests()
            .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers("/swagger-ui.html",
                "/webjars/springfox-swagger-ui/**",
                "/swagger-resources/**",
                "/v2/**",
                "/favicon.ico",
                "/health",
                "/mappings",
                "/info");
    }

}
