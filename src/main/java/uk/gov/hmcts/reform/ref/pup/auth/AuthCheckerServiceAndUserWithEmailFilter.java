package uk.gov.hmcts.reform.ref.pup.auth;

import uk.gov.hmcts.reform.auth.checker.core.RequestAuthorizer;
import uk.gov.hmcts.reform.auth.checker.core.exceptions.AuthCheckerException;
import uk.gov.hmcts.reform.auth.checker.core.service.Service;
import uk.gov.hmcts.reform.auth.checker.core.user.UserRequestAuthorizer;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;


@Slf4j
public class AuthCheckerServiceAndUserWithEmailFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final RequestAuthorizer<Service> serviceRequestAuthorizer;
    private final RequestAuthorizer<UserWithEmail> userRequestAuthorizer;

    public AuthCheckerServiceAndUserWithEmailFilter(RequestAuthorizer<Service> serviceRequestAuthorizer,
                                           RequestAuthorizer<UserWithEmail> userRequestAuthorizer) {
        this.serviceRequestAuthorizer = serviceRequestAuthorizer;
        this.userRequestAuthorizer = userRequestAuthorizer;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        Service service = authorizeService(request);
        if (service == null) {
            return null;
        }

        UserWithEmail user = authorizeUser(request);
        if (user == null) {
            return null;
        }

        return new ServiceAndUserWithEmailPair(service, user);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return request.getHeader(UserRequestAuthorizer.AUTHORISATION);
    }

    private UserWithEmail authorizeUser(HttpServletRequest request) {
        try {
            return userRequestAuthorizer.authorise(request);
        } catch (AuthCheckerException e) {
            log.warn("Unsuccessful user authentication", e);
            return null;
        }
    }

    private Service authorizeService(HttpServletRequest request) {
        try {
            return serviceRequestAuthorizer.authorise(request);
        } catch (AuthCheckerException e) {
            log.warn("Unsuccessful service authentication", e);
            return null;
        }
    }

}
