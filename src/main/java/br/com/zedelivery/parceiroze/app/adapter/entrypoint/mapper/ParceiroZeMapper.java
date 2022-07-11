package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.ParceiroZeDto;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ParceiroZeMapper {

    public ParceiroZe parceiroZeDtoToParceiroZeModel(ParceiroZeDto parceiroZeDto);
    public List<ParceiroZeDto> parceiroZeModelToParceiroZeDto(List<ParceiroZe> parceiroZeModel);
}
