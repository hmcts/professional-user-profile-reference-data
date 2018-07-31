package uk.gov.hmcts.reform.ref.pup.auth;

import uk.gov.hmcts.reform.auth.checker.core.service.Service;
import uk.gov.hmcts.reform.auth.checker.spring.serviceonly.ServiceDetails;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class AuthCheckerUserWithEmailDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
 
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        Object principal = token.getPrincipal();

        if (principal instanceof Service) {
            return new ServiceDetails(((Service) principal).getPrincipal());
        }

        if (principal instanceof UserWithEmail) {
            UserWithEmail user = (UserWithEmail) principal;
            return new UserWithEmailDetails(user.getPrincipal(), user.getEmail(), (String) token.getCredentials(), user.getRoles());
        }

        ServiceAndUserWithEmailPair serviceAndUserPair = (ServiceAndUserWithEmailPair) principal;
        return new ServiceAndUserWithEmailDetails(
            serviceAndUserPair.getUser().getPrincipal(),
            serviceAndUserPair.getUser().getEmail(),
            (String) token.getCredentials(),
            serviceAndUserPair.getUser().getRoles(),
            serviceAndUserPair.getService().getPrincipal()
        );
    }
}
