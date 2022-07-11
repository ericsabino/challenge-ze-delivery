package br.com.zedelivery.parceiroze.core.gateway;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;

import java.util.List;

public interface ParceiroZePorIdGateway {
    ParceiroZeDataproviderDto buscarParceiroZePorID(ParceiroZeDataproviderDto parceiroZeDataproviderDto);
}
