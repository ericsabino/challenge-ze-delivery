package br.com.zedelivery.parceiroze.core.usecase.impl;

import br.com.zedelivery.parceiroze.core.gateway.ParceiroZeGateway;
import br.com.zedelivery.parceiroze.core.gateway.ParceiroZePorIdGateway;
import br.com.zedelivery.parceiroze.core.usecase.ParceiroZePorIdUsecase;
import br.com.zedelivery.parceiroze.core.usecase.mapper.ParceiroZeUsecaseMapper;
import br.com.zedelivery.parceiroze.core.usecase.model.CoordenadaCliente;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ParceiroZePorIdUsecaseImpl implements ParceiroZePorIdUsecase {

    private final ParceiroZePorIdGateway parceiroZeGateway;
    private final ParceiroZeUsecaseMapper parceiroZeUsecaseMapper;

    @Override
    public ParceiroZe buscarParceiroPorId(String identificador) {
        var request = parceiroZeUsecaseMapper.identificadorToParceiroZeDataproviderDto(identificador);
        var response = parceiroZeGateway.buscarParceiroZePorID(request);
        return parceiroZeUsecaseMapper.parceiroZeDataproviderDtoToParceiroZeModel(Arrays.asList(response)).get(0);
    }
}
