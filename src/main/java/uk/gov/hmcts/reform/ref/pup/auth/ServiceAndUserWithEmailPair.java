package uk.gov.hmcts.reform.ref.pup.auth;

import uk.gov.hmcts.reform.auth.checker.core.service.Service;

import lombok.Data;

@Data
public class ServiceAndUserWithEmailPair {
    private final Service service;
    private final UserWithEmail user;
}
