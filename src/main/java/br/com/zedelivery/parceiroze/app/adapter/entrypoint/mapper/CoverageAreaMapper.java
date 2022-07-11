package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.CoverageAreaDto;
import br.com.zedelivery.parceiroze.core.usecase.model.CoverageArea;
import org.springframework.stereotype.Component;

@Component
public interface CoverageAreaMapper {

    public CoverageArea coverageAreaDtoToCovarageAreaModel(CoverageAreaDto coverageAreaDto);
    public CoverageAreaDto covarageAreaModelToCovarageAreaDto(CoverageArea coverageAreaModel);
}
