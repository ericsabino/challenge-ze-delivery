package br.com.zedelivery.parceiroze.core.gateway;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.configuration.exception.DataproviderException;

public interface ParceiroZeGateway {
    void salvarParceiroZe(ParceiroZeDataproviderDto parceiroZeDataproviderDto) throws DataproviderException;
}
