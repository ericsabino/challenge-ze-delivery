package br.com.zedelivery.parceiroze.core.usecase.impl;

import br.com.zedelivery.parceiroze.core.gateway.ParceiroZePorCoordenadasGateway;
import br.com.zedelivery.parceiroze.core.usecase.mapper.ParceiroZeUsecaseMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static br.com.zedelivery.parceiroze.mocks.MapperMocks.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ParceiroZePorCoordenadaUsecaseTest {

    @InjectMocks
    private ParceiroZePorCoordenadaUsecaseImpl parceiroZeUsecase;
    @Mock
    private ParceiroZePorCoordenadasGateway parceiroZeGateway;
    @Mock
    private ParceiroZeUsecaseMapper parceiroZeUsecaseMapper;

    @Test
    void testDeveCadastrarParceiroProximoPorCoordenadas() {
        when(parceiroZeUsecaseMapper.parceiroZeDataproviderDtoToParceiroZeModel(any())).thenReturn(Arrays.asList(getParceiroZeModel()));
        when(parceiroZeGateway.buscarParceiroZePorCoordenadas(getCoordenadaCliente())).thenReturn(Arrays.asList(getParceiroZeDataproviderDto()));
        var parceiroZe = parceiroZeUsecase.buscarParceirosProximoPorCoordenadas(getCoordenadaCliente());

        verify(parceiroZeGateway, times(1)).buscarParceiroZePorCoordenadas(getCoordenadaCliente());
        parceiroZe.forEach(p -> {
            Assert.assertEquals("002", p.getId());
            Assert.assertEquals("99.999.999/9999-00", p.getDocument());
            Assert.assertEquals("Zé", p.getTradingName());
            Assert.assertEquals("Zé Delivery", p.getOwnerName());
            Assert.assertEquals(getAddressModel(), p.getAddress());
            Assert.assertEquals(getCoverageModel(), p.getCoverageArea());
        });
    }
}