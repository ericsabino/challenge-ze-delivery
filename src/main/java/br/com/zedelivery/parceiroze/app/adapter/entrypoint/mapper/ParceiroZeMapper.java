package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.ParceiroZeDto;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;

public interface ParceiroZeMapper {

    public ParceiroZe parceiroZeDtoToParceiroZeModel(ParceiroZeDto parceiroZeDto);
    public ParceiroZeDto parceiroZeModelToParceiroZeDto(ParceiroZe parceiroZeModel);
}
