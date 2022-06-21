package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.impl;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.ParceiroZeDto;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.AddressMapper;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.CoverageAreaMapper;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.ParceiroZeMapper;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParceiroZeMapperImpl implements ParceiroZeMapper {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private CoverageAreaMapper coverageAreaMapper;

    @Override
    public ParceiroZe parceiroZeDtoToParceiroZeModel(ParceiroZeDto parceiroZeDto) {

        if(parceiroZeDto == null) {
            return null;
        }

        return ParceiroZe.builder()
                .id(parceiroZeDto.getId())
                .tradingName(parceiroZeDto.getTradingName())
                .ownerName(parceiroZeDto.getOwnerName())
                .document(parceiroZeDto.getDocument())
                .coverageArea(coverageAreaMapper.coverageAreaDtoToCovarageAreaModel(parceiroZeDto.getCoverageArea()))
                .address(addressMapper.addressDtoToAddressModel(parceiroZeDto.getAddress()))
                .build();
    }

    @Override
    public ParceiroZeDto parceiroZeModelToParceiroZeDto(ParceiroZe parceiroZeModel) {

        if(parceiroZeModel == null) {
            return null;
        }
        return ParceiroZeDto.builder()
                .id(parceiroZeModel.getId())
                .tradingName(parceiroZeModel.getTradingName())
                .ownerName(parceiroZeModel.getOwnerName())
                .document(parceiroZeModel.getDocument())
                .coverageArea(coverageAreaMapper.covarageAreaModelToCovarageAreaDto(parceiroZeModel.getCoverageArea()))
                .address(addressMapper.addressModelToAddressDto(parceiroZeModel.getAddress()))
                .build();
    }
}
