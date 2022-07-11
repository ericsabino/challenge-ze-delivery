package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.impl;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.ParceiroZeDto;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.AddressMapper;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.CoverageAreaMapper;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.ParceiroZeMapper;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    public List<ParceiroZeDto> parceiroZeModelToParceiroZeDto(List<ParceiroZe> parceiroZeModel) {

        if(parceiroZeModel == null) {
            return null;
        }
        List<ParceiroZeDto> parceirosZeDto= new ArrayList<>();
        parceiroZeModel.forEach(p -> {
            ParceiroZeDto buildParceiro = ParceiroZeDto.builder()
                    .id(p.getId())
                    .tradingName(p.getTradingName())
                    .ownerName(p.getOwnerName())
                    .document(p.getDocument())
                    .coverageArea(coverageAreaMapper.covarageAreaModelToCovarageAreaDto(p.getCoverageArea()))
                    .address(addressMapper.addressModelToAddressDto(p.getAddress()))
                    .build();

            parceirosZeDto.add(buildParceiro);
        });
        return parceirosZeDto;
    }
}
