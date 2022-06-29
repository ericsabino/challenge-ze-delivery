package br.com.zedelivery.parceiroze.core.usecase.impl;

import br.com.zedelivery.parceiroze.core.gateway.ParceiroZeGateway;
import br.com.zedelivery.parceiroze.core.usecase.ParceiroZeUsecase;
import br.com.zedelivery.parceiroze.core.usecase.mapper.ParceiroZeUsecaseMapper;
import br.com.zedelivery.parceiroze.core.usecase.model.CoordenadaCliente;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ParceiroZeUsecaseImpl implements ParceiroZeUsecase {

    private final ParceiroZeGateway parceiroZeGateway;
    private final ParceiroZeUsecaseMapper parceiroZeUsecaseMapper;

    @Override
    public void cadastrarParceiro(ParceiroZe parceiroZe) {
        var parceiroZeDataproviderDto = parceiroZeUsecaseMapper.parceiroZeToParceiroZeDataproviderDto(parceiroZe);
        parceiroZeGateway.salvarParceiroZe(parceiroZeDataproviderDto);
    }

    @Override
    public ParceiroZe buscarParceirosProximoPorCoordenadas(CoordenadaCliente coordenadaCliente) {
        var parceiroZeDataproviderDto = parceiroZeGateway.buscarParceiroZePorCoordenadas(coordenadaCliente);
        return parceiroZeUsecaseMapper.parceiroZeDataproviderDtoToParceiroZeModel(parceiroZeDataproviderDto);
    }

    @Override
    public ParceiroZe buscarParceiroPorId(String identificador) {
        var request = parceiroZeUsecaseMapper.identificadorToParceiroZeDataproviderDto(identificador);
        var response = parceiroZeGateway.buscarParceiroZePorID(request);
        return parceiroZeUsecaseMapper.parceiroZeDataproviderDtoToParceiroZeModel(response);
    }
}
