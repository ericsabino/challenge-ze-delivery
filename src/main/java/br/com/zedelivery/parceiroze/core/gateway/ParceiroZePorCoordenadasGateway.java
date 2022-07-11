package br.com.zedelivery.parceiroze.core.gateway;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import br.com.zedelivery.parceiroze.app.configuration.RedisConfig;
import br.com.zedelivery.parceiroze.core.usecase.model.CoordenadaCliente;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface ParceiroZePorCoordenadasGateway {
    static final String CACHE_MANAGER = RedisConfig.CACHE_MANAGER_ZE_DELIVERY;
    static final String CACHE_NAME = RedisConfig.PARCEIRO_ZE;

    @Cacheable(cacheManager = CACHE_MANAGER, key = "#coordenadaCliente.longitude + \"_\" + #coordenadaCliente.latitude", value = CACHE_NAME)
    List<ParceiroZeDataproviderDto> buscarParceiroZePorCoordenadas(CoordenadaCliente coordenadaCliente);
}
