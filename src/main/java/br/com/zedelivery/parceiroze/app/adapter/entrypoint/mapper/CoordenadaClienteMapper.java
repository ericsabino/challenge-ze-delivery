package br.com.zedelivery.parceiroze.app.adapter.entrypoint.mapper;

import br.com.zedelivery.parceiroze.core.usecase.model.CoordenadaCliente;
import org.springframework.stereotype.Component;

@Component
public interface CoordenadaClienteMapper {

    CoordenadaCliente coordenadaClienteDtoToCoordenadaClienteModel(Double longitude, Double latitude);

}
