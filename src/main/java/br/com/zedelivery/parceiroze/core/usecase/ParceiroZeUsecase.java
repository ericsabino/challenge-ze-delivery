package br.com.zedelivery.parceiroze.core.usecase;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.ParceiroZeDto;
import br.com.zedelivery.parceiroze.app.configuration.exception.DataproviderException;
import br.com.zedelivery.parceiroze.core.usecase.model.CoordenadaCliente;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;

public interface ParceiroZeUsecase {

    public void cadastrarParceiro(ParceiroZe parceiroZe) throws DataproviderException;

    public ParceiroZeDto buscarParceirosProximoPorCoordenadas(CoordenadaCliente coo);
}
