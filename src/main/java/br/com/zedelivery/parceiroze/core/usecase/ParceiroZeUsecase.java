package br.com.zedelivery.parceiroze.core.usecase;

import br.com.zedelivery.parceiroze.app.adapter.entrypoint.dto.ParceiroZeDto;
import br.com.zedelivery.parceiroze.core.usecase.model.CoordenadaCliente;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;

public interface ParceiroZeUsecase {

    public void cadastrarParceiro(ParceiroZe parceiroZe);

    public ParceiroZe buscarParceirosProximoPorCoordenadas(CoordenadaCliente coo);

    public ParceiroZe buscarParceiroPorId(String identificador);
}
