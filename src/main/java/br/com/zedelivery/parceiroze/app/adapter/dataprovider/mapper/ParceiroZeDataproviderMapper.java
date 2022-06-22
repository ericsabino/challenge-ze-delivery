package br.com.zedelivery.parceiroze.app.adapter.dataprovider.mapper;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity.ParceiroZeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ParceiroZeDataproviderDto.class})
public interface ParceiroZeDataproviderMapper {

    public ParceiroZeDataproviderDto parceiroZeEntityToParceiroZeDataproviderDto(ParceiroZeEntity parceiroZeEntity);

    @InheritInverseConfiguration
    public ParceiroZeEntity parceiroZeDataproviderDtoToParceiroZeEntity(ParceiroZeDataproviderDto parceiroZeDataproviderDto);
}
