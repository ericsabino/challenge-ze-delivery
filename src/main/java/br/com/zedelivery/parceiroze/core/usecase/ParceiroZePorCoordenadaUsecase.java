package br.com.zedelivery.parceiroze.core.usecase;

import br.com.zedelivery.parceiroze.core.usecase.model.CoordenadaCliente;
import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;

import java.util.List;

public interface ParceiroZePorCoordenadaUsecase {
    public List<ParceiroZe> buscarParceirosProximoPorCoordenadas(CoordenadaCliente coordenada);
}
