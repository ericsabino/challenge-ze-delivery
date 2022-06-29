package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.impl;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.CoverageAreaDto;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.CoverageAreaMapper;
import br.com.zedelivery.parceiroze.core.usecase.model.CoverageArea;
import org.springframework.stereotype.Component;

@Component
public class CoverageAreaMapperImpl implements CoverageAreaMapper {

    @Override
    public CoverageArea coverageAreaDtoToCovarageAreaModel(CoverageAreaDto coverageAreaDto) {
        if(coverageAreaDto == null) {
            return null;
        }
        return CoverageArea.builder()
                .type(coverageAreaDto.getType())
                .coordinates(coverageAreaDto.getCoordinates())
                .build();
    }

    @Override
    public CoverageAreaDto covarageAreaModelToCovarageAreaDto(CoverageArea coverageAreaModel) {
        if(coverageAreaModel == null) {
            return null;
        }
        return CoverageAreaDto.builder()
                .type(coverageAreaModel.getType())
                .coordinates(coverageAreaModel.getCoordinates())
                .build();
    }
}
