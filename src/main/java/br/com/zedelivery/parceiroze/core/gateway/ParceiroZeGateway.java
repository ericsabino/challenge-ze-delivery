package br.com.zedelivery.parceiroze.core.gateway;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.ParceiroZeDto;

public interface ParceiroZeGateway {
    void salvarParceiroZe(ParceiroZeDataproviderDto parceiroZeDataproviderDto);

    ParceiroZeDataproviderDto buscarParceiroZePorID(ParceiroZeDataproviderDto parceiroZeDataproviderDto);
}
