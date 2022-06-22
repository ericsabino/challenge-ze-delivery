package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.ParceiroZeDto;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import org.springframework.stereotype.Component;

@Component
public interface ParceiroZeMapper {

    public ParceiroZe parceiroZeDtoToParceiroZeModel(ParceiroZeDto parceiroZeDto);
    public ParceiroZeDto parceiroZeModelToParceiroZeDto(ParceiroZe parceiroZeModel);
}
