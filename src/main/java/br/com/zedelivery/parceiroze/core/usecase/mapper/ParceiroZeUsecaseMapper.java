package br.com.zedelivery.parceiroze.core.usecase.mapper;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {ParceiroZe.class})
public interface ParceiroZeUsecaseMapper {

    public ParceiroZeDataproviderDto parceiroZeToParceiroZeDataproviderDto(ParceiroZe parceiroZe);

    @InheritInverseConfiguration
    public ParceiroZe parceiroZeDataproviderDtoToParceiroZeModel(ParceiroZeDataproviderDto parceiroZeDataproviderDto);
}