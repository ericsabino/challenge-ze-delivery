package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.AddressDto;
import br.com.zedelivery.parceiroze.core.usecase.model.Address;
import org.springframework.stereotype.Component;

@Component
public interface AddressMapper {

    Address addressDtoToAddressModel(AddressDto addressDto);
    AddressDto addressModelToAddressDto(Address addressModel);

}
