package uk.gov.hmcts.reform.ref.pup.config;

import uk.gov.hmcts.reform.auth.checker.core.CachingSubjectResolver;
import uk.gov.hmcts.reform.auth.checker.core.SubjectResolver;
import uk.gov.hmcts.reform.auth.checker.core.user.UserRequestAuthorizer;
import uk.gov.hmcts.reform.auth.checker.spring.AuthCheckerProperties;
import uk.gov.hmcts.reform.auth.parser.idam.core.user.token.HttpComponentsBasedUserTokenParser;
import uk.gov.hmcts.reform.ref.pup.auth.AuthCheckerUserWithEmailDetailsService;
import uk.gov.hmcts.reform.ref.pup.auth.UserWithEmail;
import uk.gov.hmcts.reform.ref.pup.auth.UserWithEmailResolver;
import uk.gov.hmcts.reform.ref.pup.auth.UserWithEmailTokenDetails;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

@Lazy
@Configuration
public class AuthCheckerConfiguration {

    @Value("#{'${authorization.s2s-names-whitelist}'.split(',')}")
    private List<String> s2sNamesWhiteList;

    @Value("#{'${authorization.idam-roles-whitelist}'.split(',')}")
    private List<String> idamRolesWhitelist;


    @Bean
    public Function<HttpServletRequest, Collection<String>> authorizedServicesExtractor() {
        return any -> s2sNamesWhiteList;
    }

    @Bean
    public Function<HttpServletRequest, Collection<String>> authorizedRolesExtractor() {
        return any -> idamRolesWhitelist;
    }

    @Bean
    public Function<HttpServletRequest, Optional<String>> userIdExtractor() {
        return request -> Optional.empty();
    }
    
    @Bean
    @Primary
    public SubjectResolver<UserWithEmail> userResolver(AuthCheckerProperties properties, HttpClient userTokenParserHttpClient, @Value("${auth.idam.client.baseUrl}") String baseUrl) {
        HttpComponentsBasedUserTokenParser<UserWithEmailTokenDetails> userTokenParser = new HttpComponentsBasedUserTokenParser<>(userTokenParserHttpClient, baseUrl, UserWithEmailTokenDetails.class);
        
        return new CachingSubjectResolver<>(new UserWithEmailResolver(userTokenParser), properties.getUser().getTtlInSeconds(), properties.getUser().getMaximumSize());
    }

    @Bean
    public UserRequestAuthorizer<UserWithEmail> userRequestAuthorizer(SubjectResolver<UserWithEmail> userResolver,
                                                       Function<HttpServletRequest, Optional<String>> userIdExtractor,
                                                       Function<HttpServletRequest, Collection<String>> authorizedRolesExtractor) {
        return new UserRequestAuthorizer<UserWithEmail>(userResolver, userIdExtractor, authorizedRolesExtractor);
    }
    
    @Bean
    public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
        PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
        authenticationProvider.setPreAuthenticatedUserDetailsService(new AuthCheckerUserWithEmailDetailsService());
        return authenticationProvider;
    }
    
}
