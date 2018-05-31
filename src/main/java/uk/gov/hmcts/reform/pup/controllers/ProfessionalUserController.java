package uk.gov.hmcts.reform.pup.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.pup.services.ProfessionalUserProfileService;

import javax.validation.Valid;
import java.util.UUID;

@RestController()
@RequestMapping("pup/user")
public class ProfessionalUserController {

    private ProfessionalUserProfileService professionalUserProfileService;

    @Autowired
    public ProfessionalUserController(ProfessionalUserProfileService professionalUserProfileService) {
        this.professionalUserProfileService = professionalUserProfileService;
    }

    @PostMapping(value = "")
    @ApiOperation("Create Professional User.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = ProfessionalUser.class)
    })
    public ResponseEntity<ProfessionalUser> createProfessionalUser(@RequestBody @Valid ProfessionalUser body) {
        return ResponseEntity.ok(professionalUserProfileService.createProfessionalUser(body));
    }

    @GetMapping(value = "{uuid}")
    @ApiOperation("Retrieve Professional User.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = ProfessionalUser.class)
    })
    public ResponseEntity<ProfessionalUser> getMarketStall(@PathVariable UUID uuid) {
        ProfessionalUser professionalUser = professionalUserProfileService.retrieveProfessionalUser(uuid);
        if (professionalUser != null) {
            return ResponseEntity.ok(professionalUser);
        } else {
            return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "{uuid}")
    @ApiOperation("Delete Professional User.")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "No Content")
    })
    public ResponseEntity<ProfessionalUser> deleteMarketStall(@PathVariable UUID uuid) {
        professionalUserProfileService.deleteProfessionalUser(uuid);
        return ResponseEntity.noContent().build();
    }



    //    get a user
//    post a user
//    put user
//    delete user

//    post a pba
//    get a pba
//    delete pba

//    post a link user and pba
//    delete user and pba link
}
