package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.impl;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper.CoordenadaClienteMapper;
import br.com.zedelivery.parceiroze.core.usecase.model.CoordenadaCliente;
import org.springframework.stereotype.Component;

@Component
public class CoordenadaClienteMapperImpl implements CoordenadaClienteMapper {

    @Override
    public CoordenadaCliente coordenadaClienteDtoToCoordenadaClienteModel(Double longitude, Double latitude) {
        return CoordenadaCliente.builder()
                .longitude(longitude)
                .latitude(latitude)
                .build();
    }
}
