package uk.gov.hmcts.reform.pup.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.pup.services.ProfessionalUserProfileService;

@RestController()
@RequestMapping("pup")
public class ProfessionalUserProfileController {

    private ProfessionalUserProfileService professionalUserProfileService;

    @Autowired
    public ProfessionalUserProfileController(ProfessionalUserProfileService professionalUserProfileService) {
        this.professionalUserProfileService = professionalUserProfileService;
    }

//    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ProfessionalUserProfile> newSession(
//            @RequestBody final ProfessionalUserProfile professionalUserProfile) throws URISyntaxException {
//        String location = "/icp/sessions/" + this.hearingSessionService.newSession(hearingSession).getId();
//        return ResponseEntity.created(new URI(location)).body(hearingSession);
//    }
//
//    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<HearingSession> getSession(@PathVariable UUID id) {
//        return ResponseEntity.ok(this.hearingSessionService.getSession(id));
//    }

}
