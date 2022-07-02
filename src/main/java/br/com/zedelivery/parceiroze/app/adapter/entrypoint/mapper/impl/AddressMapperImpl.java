package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.impl;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.AddressDto;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.AddressMapper;
import br.com.zedelivery.parceiroze.core.usecase.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapperImpl implements AddressMapper {
    @Override
    public Address addressDtoToAddressModel(AddressDto addressDto) {
        if(addressDto == null) {
            return null;
        }
        return Address.builder()
                .type(addressDto.getType())
                .coordinates(addressDto.getCoordinates())
                .build();
    }

    @Override
    public AddressDto addressModelToAddressDto(Address addressModel) {
        if(addressModel == null) {
            return null;
        }
        return AddressDto.builder()
                .type(addressModel.getType())
                .coordinates(addressModel.getCoordinates())
                .build();
    }
}
