package br.com.zedelivery.parceiroze.core.usecase.impl;

import br.com.zedelivery.parceiroze.core.gateway.ParceiroZePorCoordenadasGateway;
import br.com.zedelivery.parceiroze.core.usecase.ParceiroZePorCoordenadaUsecase;
import br.com.zedelivery.parceiroze.core.usecase.mapper.ParceiroZeUsecaseMapper;
import br.com.zedelivery.parceiroze.core.usecase.model.CoordenadaCliente;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ParceiroZePorCoordenadaUsecaseImpl implements ParceiroZePorCoordenadaUsecase {
    private ParceiroZePorCoordenadasGateway parceiroZeGateway;
    private final ParceiroZeUsecaseMapper parceiroZeUsecaseMapper;

    @Override
    public List<ParceiroZe> buscarParceirosProximoPorCoordenadas(CoordenadaCliente coordenadaCliente) {
        var parceiroZeDataproviderDto = parceiroZeGateway.buscarParceiroZePorCoordenadas(coordenadaCliente);
        return parceiroZeUsecaseMapper.parceiroZeDataproviderDtoToParceiroZeModel(parceiroZeDataproviderDto);
    }
}
