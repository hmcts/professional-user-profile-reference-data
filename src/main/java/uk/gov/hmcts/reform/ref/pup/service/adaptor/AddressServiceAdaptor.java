package uk.gov.hmcts.reform.ref.pup.service.adaptor;

import uk.gov.hmcts.reform.ref.pup.converter.AddressConverter;
import uk.gov.hmcts.reform.ref.pup.dto.AddressCreation;
import uk.gov.hmcts.reform.ref.pup.dto.AddressDto;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import javax.transaction.Transactional;

@Service
@Transactional
public class AddressServiceAdaptor {

    private final AddressService addressService;
    private final AddressConverter addressConverter;

    @Autowired
    public AddressServiceAdaptor(AddressService addressService, AddressConverter addressConverter) {
        this.addressService = addressService;
        this.addressConverter = addressConverter;
    }

    public AddressDto create(UUID organisationUuid, AddressCreation address) throws ApplicationException {
        return addressConverter.apply(addressService.create(organisationUuid, address));
    }

}