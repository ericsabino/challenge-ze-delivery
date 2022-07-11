package br.com.zedelivery.parceiroze.core.gateway;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;

public interface ParceiroZeGateway {

    void salvarParceiroZe(ParceiroZeDataproviderDto parceiroZeDataproviderDto);

}
