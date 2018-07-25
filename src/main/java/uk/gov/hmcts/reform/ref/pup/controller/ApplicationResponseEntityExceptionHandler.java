package uk.gov.hmcts.reform.ref.pup.controller;

import uk.gov.hmcts.reform.ref.pup.dto.ReasonResponse;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException.ApplicationErrorCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class ApplicationResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    public ApplicationResponseEntityExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ex.getBindingResult().getAllErrors(), headers, status, request);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        ApplicationErrorCode campaignErrorCode = ((ApplicationException) ex).getApplicationErrorCode();
        String errorCodeFullName = ex.getClass().getSimpleName() + "." + campaignErrorCode.name();

        ReasonResponse reasonResponse = new ReasonResponse(messageSource.getMessage(errorCodeFullName, null, Locale.ENGLISH), ((ApplicationException) ex).getMessage());

        return new ResponseEntity<>(reasonResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


}