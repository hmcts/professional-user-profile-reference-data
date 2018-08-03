package uk.gov.hmcts.reform.ref.pup.service.impl;

import uk.gov.hmcts.reform.ref.pup.domain.Address;
import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.dto.AddressCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException.ApplicationErrorCode;
import uk.gov.hmcts.reform.ref.pup.repository.AddressRepository;
import uk.gov.hmcts.reform.ref.pup.repository.OrganisationRepository;
import uk.gov.hmcts.reform.ref.pup.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import javax.transaction.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final OrganisationRepository organisationRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public Address create(UUID organisationUuid, AddressCreation addressCreation) throws ApplicationException {

        Organisation organisation = organisationRepository.findById(organisationUuid)
                                        .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.ORGANISATION_ID_DOES_NOT_EXIST));

        Address address = new Address();
        address.setOrganisation(organisation);
        address.setAddressType(addressCreation.getType());
        address.setAddressLine1(addressCreation.getAddressLine1());
        address.setAddressLine2(addressCreation.getAddressLine2());
        address.setAddressLine3(addressCreation.getAddressLine3());
        address.setCity(addressCreation.getCity());
        address.setCountry(addressCreation.getCountry());
        address.setCounty(addressCreation.getCounty());

        return addressRepository.save(address);
    }
}
