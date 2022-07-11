package br.com.zedelivery.parceiroze.core.usecase;

import br.com.zedelivery.parceiroze.core.usecase.model.ParceiroZe;

public interface ParceiroZePorIdUsecase {
    public ParceiroZe buscarParceiroPorId(String identificador);
}
