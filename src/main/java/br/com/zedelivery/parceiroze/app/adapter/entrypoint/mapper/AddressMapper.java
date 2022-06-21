package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.AddressDto;
import br.com.zedelivery.parceiroze.core.usecase.model.Address;


public interface AddressMapper {

    public Address addressDtoToAddressModel(AddressDto addressDto);
    public AddressDto addressModelToAddressDto(Address addressModel);

}
