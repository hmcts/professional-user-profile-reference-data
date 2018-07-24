package uk.gov.hmcts.reform.ref.pup.controller;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.dto.AddressCreation;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.services.OrganisationService;

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
import java.util.UUID;

import javax.validation.Valid;

@RestController
@RequestMapping("pup/organisation")
public class OrganisationController {

    private static final ResponseEntity<Organisation> NOT_FOUND_RESPONSE = ResponseEntity.notFound().build();
    private final OrganisationService organisationService;

    @Autowired
    public OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @PostMapping
    @ApiOperation("Create Organisation.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Organisation.class)
    })
    public ResponseEntity<Organisation> createOrganisation(@RequestBody @Valid OrganisationCreation organisation) throws ApplicationException {
        return ResponseEntity.ok(organisationService.create(organisation));
    }

    @GetMapping(value = "{organisationUuid}")
    @ApiOperation("Retrieve Organisation.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = Organisation.class)
    })
    public ResponseEntity<Organisation> getOrganisation(@PathVariable String organisationUuid) throws ApplicationException {
        Optional<Organisation> organisation = organisationService.retrieve(UUID.fromString(organisationUuid));
        if (!organisation.isPresent()) {
            return NOT_FOUND_RESPONSE;
        }

        return ResponseEntity.ok(organisation.get());
    }

    @DeleteMapping(value = "{organisationUuid}")
    @ApiOperation("Delete Organisation.")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "No Content")
    })
    public ResponseEntity<Organisation> deleteOrganisation(@PathVariable String organisationUuid) throws ApplicationException {
        organisationService.delete(UUID.fromString(organisationUuid));
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("{organisationUuid}/address")
    @ApiOperation("Create Address.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Organisation.class)
    })
    public ResponseEntity<Organisation> addOrganisationAddress(
            @PathVariable String organisationUuid,
            @RequestBody @Valid AddressCreation address) throws ApplicationException {
        
        return ResponseEntity.ok(organisationService.addAddress(UUID.fromString(organisationUuid), address));
    }

}
