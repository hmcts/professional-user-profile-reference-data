package uk.gov.hmcts.reform.ref.pup.services.impl;

import uk.gov.hmcts.reform.ref.pup.domain.Address;
import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.dto.AddressCreation;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException.ApplicationErrorCode;
import uk.gov.hmcts.reform.ref.pup.repository.OrganisationRepository;
import uk.gov.hmcts.reform.ref.pup.services.OrganisationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrganisationServiceImpl implements OrganisationService {

    private final OrganisationRepository organisationRepository;
    
    @Autowired
    public OrganisationServiceImpl(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    @Override
    public Organisation create(final OrganisationCreation organisationInput) throws ApplicationException {
        Optional<Organisation> organisationByName = organisationRepository.findOneByName(organisationInput.getName());
        if (organisationByName.isPresent()) {
            throw new ApplicationException(ApplicationErrorCode.ORGANISATION_ID_IN_USE);
        }
        
        Organisation organisation = new Organisation();
        organisation.setName(organisationInput.getName());
        
        return organisationRepository.save(organisation);
    }

    @Override
    public Optional<Organisation> retrieve(UUID uuid) throws ApplicationException {
        return organisationRepository.findById(uuid);
    }

    @Override
    public void delete(UUID uuid) throws ApplicationException {
        organisationRepository.deleteById(uuid);
    }
    
    @Override
    public Organisation addAddress(UUID uuid, AddressCreation addressCreation) throws ApplicationException {

        Organisation organisation = organisationRepository.findById(uuid)
                                        .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.ORGANISATION_ID_DOES_NOT_EXIST));
        
        Address address = new Address();
        address.setOrganisation(organisation);
        address.setAddressLine1(addressCreation.getAddressLine1());
        address.setAddressLine2(addressCreation.getAddressLine2());
        address.setAddressLine3(addressCreation.getAddressLine3());
        address.setCity(addressCreation.getCity());
        address.setCountry(addressCreation.getCountry());
        address.setCounty(addressCreation.getCounty());
        
        organisation.getAddresses().add(address);
        
        return organisationRepository.save(organisation);
    }
}
