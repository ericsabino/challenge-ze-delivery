package br.com.zedelivery.parceiroze.app.adapter.dataprovider.mapper;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity.ParceiroZeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParceiroZeDataproviderMapper {

    public List<ParceiroZeDataproviderDto> parceiroZeEntityToParceiroZeDataproviderDto(List<ParceiroZeEntity> parceiroZeEntity);

    @InheritInverseConfiguration
    @Mapping(target = "id",ignore = true)
    public List<ParceiroZeEntity> parceiroZeDataproviderDtoToParceiroZeEntity(List<ParceiroZeDataproviderDto> parceiroZeDataproviderDto);
}
