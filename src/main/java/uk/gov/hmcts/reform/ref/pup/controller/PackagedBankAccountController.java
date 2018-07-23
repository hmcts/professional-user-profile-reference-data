package uk.gov.hmcts.reform.ref.pup.controller;

import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.PaymentAccountCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.services.PaymentAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("pup/pba")
public class PackagedBankAccountController {

    private static final ResponseEntity<PaymentAccount> NOT_FOUND_RESPONSE = ResponseEntity.notFound().build();
    private final PaymentAccountService paymentAccountService;

    @Autowired
    public PackagedBankAccountController(PaymentAccountService paymentAccountService) {
        this.paymentAccountService = paymentAccountService;
    }

    @PostMapping
    @ApiOperation("Create Payment Account.")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = ProfessionalUser.class) 
    })
    public ResponseEntity<PaymentAccount> createPaymentAccount(@RequestBody @Valid PaymentAccountCreation paymentAccount) throws ApplicationException {
        return ResponseEntity.ok(paymentAccountService.create(paymentAccount));
    }

    @GetMapping(value = "{uuid}")
    @ApiOperation("Retrieve Payment Account.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = ProfessionalUser.class)
    })
    public ResponseEntity<PaymentAccount> getProfessionalUser(@PathVariable String uuid) throws ApplicationException {
        
        Optional<PaymentAccount> paymentAccount = paymentAccountService.retrieve(uuid);
        
        if (!paymentAccount.isPresent()) {
            return  NOT_FOUND_RESPONSE;
        }

        return ResponseEntity.ok(paymentAccount.get());
    }

    @DeleteMapping(value = "{uuid}")
    @ApiOperation("Delete Payment Account.")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "No Content")
    })
    public ResponseEntity<PaymentAccount> deletePaymentAccount(@PathVariable String uuid) throws ApplicationException {
        
        paymentAccountService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

//    post a link user and pba
//    delete user and pba link
}
