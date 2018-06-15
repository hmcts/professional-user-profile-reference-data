package uk.gov.hmcts.reform.ref.pup.componenttests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import uk.gov.hmcts.reform.auth.checker.core.RequestAuthorizer;
import uk.gov.hmcts.reform.auth.checker.core.SubjectResolver;
import uk.gov.hmcts.reform.auth.checker.core.service.Service;
import uk.gov.hmcts.reform.auth.checker.core.user.User;
import uk.gov.hmcts.reform.auth.checker.spring.serviceanduser.AuthCheckerServiceAndUserFilter;
import uk.gov.hmcts.reform.ref.pup.componenttests.backdoors.ServiceResolverBackdoor;
import uk.gov.hmcts.reform.ref.pup.componenttests.backdoors.UserResolverBackdoor;

@Configuration
public class ComponentTestConfiguration {

    @Bean
    public SubjectResolver<Service> serviceResolver() {
        return new ServiceResolverBackdoor();
    }

    @Bean
    public SubjectResolver<User> userResolver() {
        return new UserResolverBackdoor();
    }

    @Bean
    public AuthCheckerServiceAndUserFilter authCheckerServiceFilter(RequestAuthorizer<Service> serviceRequestAuthorizer,
                                                                    RequestAuthorizer<User> userRequestAuthorizer,
                                                                    AuthenticationManager authenticationManager) {
        AuthCheckerServiceAndUserFilter filter = new AuthCheckerServiceAndUserFilter(serviceRequestAuthorizer,userRequestAuthorizer);
        filter.setAuthenticationManager(authenticationManager);
        filter.setCheckForPrincipalChanges(true);
        filter.setInvalidateSessionOnPrincipalChange(true);
        return filter;
    }

}
