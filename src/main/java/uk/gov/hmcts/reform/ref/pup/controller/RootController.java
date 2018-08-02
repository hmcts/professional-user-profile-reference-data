package uk.gov.hmcts.reform.ref.pup.controller;

import uk.gov.hmcts.reform.auth.checker.spring.serviceanduser.ServiceAndUserDetails;
import uk.gov.hmcts.reform.ref.pup.dto.PaymentAccountDto;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserFullDetailDto;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.service.adaptor.ProfessionalUserFullDetailServiceAdaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Optional;

@Controller
@RequestMapping("pup")
public class RootController {

    private static final ResponseEntity<ProfessionalUserFullDetailDto> NOT_FOUND_RESPONSE = ResponseEntity.notFound().build();

    private final ProfessionalUserFullDetailServiceAdaptor professionalUserService;

    @Autowired
    public RootController(ProfessionalUserFullDetailServiceAdaptor professionalUserService) {
        this.professionalUserService = professionalUserService;
    }

    @GetMapping(value = "mine")
    @ApiOperation("Retrieve my full profile")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = PaymentAccountDto.class)
    })
    public ResponseEntity<ProfessionalUserFullDetailDto> myFullDetail(@AuthenticationPrincipal ServiceAndUserDetails userDetails) throws ApplicationException {

        Optional<ProfessionalUserFullDetailDto> professionalUser = professionalUserService.retrieve(userDetails.getUsername());

        if (!professionalUser.isPresent()) {
            return NOT_FOUND_RESPONSE;
        }

        return ResponseEntity.ok(professionalUser.get());
    }

}
