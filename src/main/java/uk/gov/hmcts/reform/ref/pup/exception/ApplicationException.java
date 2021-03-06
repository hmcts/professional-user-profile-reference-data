package uk.gov.hmcts.reform.ref.pup.exception;

import java.util.Objects;

public class ApplicationException extends Exception {

    private static final long serialVersionUID = 1L;

    public enum ApplicationErrorCode {
        ORGANISATION_ID_IN_USE, ORGANISATION_ID_DOES_NOT_EXIST,
        PAYMENT_ACCOUNT_ID_IN_USE, PAYMENT_ACCOUNT_ID_DOES_NOT_EXIST,
        PROFESSIONAL_USER_ID_IN_USE, PROFESSIONAL_USER_ID_DOES_NOT_EXIST,
        PAYMENT_ACCOUNT_ALREADY_ASSIGNED, PAYMENT_ACCOUNT_IS_NOT_ASSIGNED,
        ADDRESS_ID_DOES_NOT_EXIST,
        PAYMENT_ACCOUNT_CAN_NOT_BE_UNASSIGNED, PAYMENT_ACCOUNT_CAN_NOT_BE_ASSIGNED
    }

    private final ApplicationErrorCode applicationErrorCode;

    public ApplicationException(final ApplicationErrorCode applicationErrorCode) {
        super(Objects.toString(applicationErrorCode));
        this.applicationErrorCode = applicationErrorCode;
    }

    public ApplicationErrorCode getApplicationErrorCode() {
        return applicationErrorCode;
    }

}
