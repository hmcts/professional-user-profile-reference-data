package uk.gov.hmcts.reform.ref.pup.auth;

import uk.gov.hmcts.reform.auth.checker.core.SubjectResolver;
import uk.gov.hmcts.reform.auth.parser.idam.core.user.token.UserTokenParser;

public class UserWithEmailResolver implements SubjectResolver<UserWithEmail> {

    private final UserTokenParser<UserWithEmailTokenDetails> userTokenParser;

    public UserWithEmailResolver(UserTokenParser<UserWithEmailTokenDetails> userTokenParser) {
        this.userTokenParser = userTokenParser;
    }

    @Override
    public UserWithEmail getTokenDetails(String bearerToken) {
        UserWithEmailTokenDetails details = userTokenParser.parse(bearerToken);
        return new UserWithEmail(details.getId(), details.getRoles(), details.getEmail());
    }
    

}
